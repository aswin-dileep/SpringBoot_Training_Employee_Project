import { Component } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

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
    status: '',
    departmentId: null,
    phoneNumber: '',
    salary: null,
    hireDate: ''
   
  };

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  onSubmit(): void {

    console.log(this.employee);

    this.employeeService.createEmployee(this.employee)
      .subscribe({
        next: () => {
          alert('Employee created successfully');
          this.router.navigate(['/dashboard']);
        },
        error: err => {
          console.error(err);
          alert('Failed to create employee');
        }
      });
  }
}
