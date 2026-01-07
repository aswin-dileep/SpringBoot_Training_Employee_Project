import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.component.html',
  styleUrls: ['./view-employee.component.css']
})
export class ViewEmployeeComponent implements OnInit {

   employee: any;
  loading = true;

  constructor(private route: ActivatedRoute,
    private employeeService: EmployeeService) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.employeeService.getEmployeeById(id).subscribe({
      next: (res) => {
        this.employee = res;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

}
