import { Component,Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { EmployeeService } from 'src/app/employee.service';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private router: Router,private authService:AuthService,private employeeService: EmployeeService) {}


  @Input() selectedEmployee: any;


  logout(): void {
    this.authService.logout();
  }

  update() {
  this.router.navigate(['/employee/update', this.selectedEmployee.id]);
 }

 
 view() {
    if (!this.selectedEmployee) {
      return;
    }

    this.router.navigate([
      '/employee/view',
      this.selectedEmployee.id
    ]);
  }

 delete() {
  if (confirm('Are you sure you want to delete this employee?')) {
    this.employeeService
      .deleteEmployee(this.selectedEmployee.id)
      .subscribe(() => {
        alert('Employee deleted');
        window.location.reload();
      });
  }
 }

  



  navigate(action: string) {
    switch (action) {
      case 'create':
        this.router.navigate(['/employee/create']);
        break;
      case 'update':
        this.router.navigate(['/employee/update']);
        break;
      case 'delete':
        this.router.navigate(['/employee/delete']);
        break;
      case 'view':
        this.router.navigate(['/dashboard']);
        break;
    }
  }
}
