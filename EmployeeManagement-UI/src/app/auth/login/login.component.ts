import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username = '';
  password = '';
  errorMessage = '';
  loading = false;


  constructor(private router: Router,private authService:AuthService ) {}

  login() {
    this.errorMessage = '';
    this.loading = true;

    console.log('Username:', this.username);
    console.log('Password:', this.password);

    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        
        // Keycloak returns access_token
        this.authService.storeToken(response.access_token);
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.errorMessage = 'Invalid username or password';
        this.loading = false;
      }
    });
  }
}
