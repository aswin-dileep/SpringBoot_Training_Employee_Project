import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { ToastrService } from 'ngx-toastr';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse // Recommended for type safety in catchError
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs'; // Added throwError
import { catchError, switchMap } from 'rxjs/operators'; // Added operators
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private isRefreshing = false;

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    // ⛔ DO NOT touch login request
    if (req.url.includes('/auth/login') || req.url.includes('/auth/refresh')) {
      return next.handle(req);
    }

    const token = localStorage.getItem('access_token');

    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req).pipe(
      catchError(error => {
        if (error.status === 401 && !this.isRefreshing) {
          this.isRefreshing = true;

          return this.authService.refreshToken().pipe(
            switchMap(res => {
                this.isRefreshing = false;

                this.authService.storeToken(res);

                const newAccessToken = res.access_token ?? res.accessToken;

               const clonedReq = req.clone({
                  setHeaders: {
                    Authorization: `Bearer ${newAccessToken}`
                }
        });

        return next.handle(clonedReq);
        }),
            catchError(err => {
              this.isRefreshing = false;
              this.authService.logout();
              return throwError(err);
            })
          );
        }

        return throwError(error);
      })
    );
  }
}