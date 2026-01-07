import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  employees: any[] = [];
  selectedEmployee: any = null;

  pageIndex = 0;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;
  

 filters = {
  name: '',
  status: '',
  departmentId: '',
  minSalary: '',
  maxSalary: ''
};

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  selectEmployee(emp: any) {
  this.selectedEmployee = emp;
}
  loadEmployees(): void {
    this.employeeService
      .getEmployees(this.pageIndex, this.pageSize, this.filters)
      .subscribe(res => {
        this.employees = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
      });
  }

  nextPage(): void {
    if (this.pageIndex + 1 < this.totalPages) {
      this.pageIndex++;
      this.loadEmployees();
    }
  }

  applyFilters(): void {
  this.pageIndex = 0;   
  this.loadEmployees();
}

  prevPage(): void {
    if (this.pageIndex > 0) {
      this.pageIndex--;
      this.loadEmployees();
    }
  }
}
