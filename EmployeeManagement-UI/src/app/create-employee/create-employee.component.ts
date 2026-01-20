import { Component } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent {

  employee = {
    firstName: '',
    lastName:'',
    email: '',
    status: null,
    departmentId: null,
    phoneNumber: '',
    salary: null,
    hireDate: ''
   
  };

  backendErrors: { [key: string]: string[] } = {};

  constructor(
    private employeeService: EmployeeService,
    private router: Router,
    private toastr:ToastrService
  ) {}

  onSubmit(): void {
    this.backendErrors = {};
    this.employeeService.createEmployee(this.employee)
      .subscribe({
        next: () => {
          this.toastr.success('Employee created successfully');
          this.router.navigate(['/dashboard']);
        },
        error: err => {
          
          if (err.status === 400) {
            console.log(err.error);
            this.backendErrors = err.error; 
          } else {
            console.log('hey');
            this.toastr.error(err.error?.message || 'Failed to create employee');
          }
        }
      });
  }
}
