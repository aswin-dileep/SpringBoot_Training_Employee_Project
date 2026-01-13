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
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      departmentID: ['', Validators.required],
      salary: ['', Validators.required],
      status: ['', Validators.required],
      hireDate: ['', Validators.required]
    });

    this.employeeService.getEmployeeById(this.employeeId).subscribe(res => {
      this.employeeForm.patchValue(res);
    });
  }

  updateEmployee() {
    if (this.employeeForm.invalid) return;

    this.employeeService
      .updateEmployee(this.employeeId, this.employeeForm.value)
      .subscribe({
        next: () => {
          this.toastr.success('Employee updated successfully');
          this.router.navigate(['/dashboard']);
        },
        error: () => this.toastr.error('Update failed')
      });
  }
}
