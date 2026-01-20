import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {

  employeeForm!: FormGroup;
  employeeId!: number;

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {

    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));

    this.employeeForm = this.fb.group({
      firstName: '',
      lastName:'',
      email: '',
      phoneNumber: '',
     departmentId: ['', Validators.required], 
      salary: '',
      status: '',     
      hireDate: ''
    });

    this.employeeService.getEmployeeById(this.employeeId).subscribe(res => {
      this.employeeForm.patchValue(res);
    });
  }

  updateEmployee(): void {
    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      return;
    }

    this.employeeService
      .updateEmployee(this.employeeId, this.employeeForm.value)
      .subscribe({
        next: () => {
          this.toastr.success('Employee updated successfully');
          this.router.navigate(['/dashboard']);
        },
        error: err => {
          if (err.status === 400 && typeof err.error === 'object') {
            this.applyBackendErrors(err.error);
          } else {
            this.toastr.error(err.error?.message || 'Update failed');
          }
        }
      });
  }

  private applyBackendErrors(errors: { [key: string]: string[] }) {
    Object.keys(errors).forEach(field => {
      const control = this.employeeForm.get(field);
      if (control) {
        control.setErrors({ backend: errors[field][0] });
      }
    });
  }
}
