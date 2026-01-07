import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = "http://localhost:8282/auth/login";
  constructor(private http: HttpClient,private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}`, {
      username,
      password
    });
  }

  refreshToken() {
  const refreshToken = localStorage.getItem('refresh_token');

  return this.http.post<any>(
    'http://localhost:8282/auth/refresh',
    { refreshToken }
  );
}

  storeToken(token: any): void {
  // Handle Keycloak-style response
  const accessToken = token.access_token ?? token.accessToken;
  const refreshToken = token.refresh_token ?? token.refreshToken;
  const expiresIn = token.expires_in ?? token.expiresIn;

  if (!accessToken || !refreshToken) {
    console.error('Invalid token response:', token);
    return;
  }

  localStorage.setItem('access_token', accessToken);
  localStorage.setItem('refresh_token', refreshToken);
  localStorage.setItem('expires_in', expiresIn?.toString() ?? '');
}

  getToken(): string|null{
    return localStorage.getItem('access_token');
  }

  logout():void{
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('expires_in');
    this.router.navigate(['/login']);
  }

  isLoggedIn():boolean{
    return !!this.getToken();
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('access_token');
  }
}
