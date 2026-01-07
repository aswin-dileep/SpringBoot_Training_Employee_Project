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

  storeToken(token: string): void {
    localStorage.setItem('access_token', token);
  }

  getToken(): string|null{
    return localStorage.getItem('access_token');
  }

  logout():void{
    localStorage.removeItem('access_token');
     this.router.navigate(['/login']);
  }

  isLoggedIn():boolean{
    return !!this.getToken();
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('access_token');
  }
}
