import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { ToastrService } from 'ngx-toastr';

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


  constructor(private router: Router,private authService:AuthService,private toastr: ToastrService, ) {}

  login() {
    this.errorMessage = '';
    this.loading = true;

    console.log('Username:', this.username);
    console.log('Password:', this.password);

    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        this.authService.storeToken(response);
        this.loading = false;             
        this.toastr.success('Login successful', 'Success');
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.toastr.error('Invalid username or password', 'Error');
        this.loading = false;
      }
    });
  }
}
