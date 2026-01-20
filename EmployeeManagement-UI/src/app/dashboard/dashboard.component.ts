import { Component, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { EmployeeService } from '../employee.service';
import * as moment from 'moment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  employees: any[] = [];
  selectedEmployee: any = null;
  dataTable: any;

  // pageIndex = 0;
  // pageSize = 5;
  // totalElements = 0;
  // totalPages = 0;

  hireDateRange = {
    startDate: null,
    endDate: null
  };

  filters = {
    name: '',
    status: '',
    departmentId: null,
    minSalary: null,
    maxSalary: null
  };


  constructor(private employeeService: EmployeeService) { }



  ngOnInit(): void {
    // this.loadEmployees();
  }

  ngAfterViewInit(): void {
    this.initDataTable();
  }

  initDataTable(): void {
    const self = this;

    if (typeof ($) === 'undefined') {
      console.error('jQuery not loaded!');
      return;
    }

    this.dataTable = ($('#employeeTable') as any).DataTable({
      processing: true,
      serverSide: true,
      searching: false,
      pageLength: 5,

      ajax: (params: any, callback: any) => {
        // const page = Math.floor(params.start / params.length);
        console.log(params);
        const requestParams: any = {
          page: Math.floor(params.start / params.length),
          size: params.length,
          search: this.filters.name || '',
          status: this.filters.status || '',
          departmentId: this.filters.departmentId,
          minSalary: this.filters.minSalary,
          maxSalary: this.filters.maxSalary
        };

        if (this.hireDateRange?.startDate && this.hireDateRange?.endDate) {
          requestParams.startDate = moment(this.hireDateRange.startDate).format('YYYY-MM-DD');
          requestParams.endDate = moment(this.hireDateRange.endDate).format('YYYY-MM-DD');
        }

        if (this.filters.departmentId !== null) {
          requestParams.departmentId = this.filters.departmentId;
        }

        if (this.filters.minSalary !== null) {
          requestParams.minSalary = this.filters.minSalary;
        }

        if (this.filters.maxSalary !== null) {
          requestParams.maxSalary = this.filters.maxSalary;
        }

        self.employeeService.getEmployees(requestParams)
          .subscribe(resp => {
            callback({
              draw: params.draw,
              recordsTotal: resp.totalElements,
              recordsFiltered: resp.totalElements,
              data: resp.content
            });
          });
      },

      columns: [
        {
          data: null,
          render: () => `<input type="radio" name="selectedEmployee">`
        },
        {
          data: null,
          render: (d: any) => `${d.firstName} ${d.lastName}`
        },
        { data: 'email' },
        { data: 'status' },
        { data: 'departmentName' },
        { data: 'salary' }
      ]
    });

    $('#employeeTable tbody').on('click', 'tr', function () {
      const rowData = self.dataTable.row(this).data();
      self.selectedEmployee = rowData;

      $('input[name="selectedEmployee"]').prop('checked', false);
      $(this).find('input[name="selectedEmployee"]').prop('checked', true);
    });
  }

  selectEmployee(emp: any) {
    this.selectedEmployee = emp;
  }

  // loadEmployees(): void {
  //   this.employeeService
  //     .getEmployees(this.pageIndex, this.pageSize, this.filters)
  //     .subscribe(res => {
  //       this.employees = res.content;
  //       this.totalElements = res.totalElements;
  //       this.totalPages = res.totalPages;
  //     });
  // }

  applyFilters(): void {
    this.selectedEmployee = null;
    this.dataTable.ajax.reload();
  }

  resetFilter(): void {
    this.filters = {
      name: '',
      status: '',
      departmentId: null,
      minSalary: null,
      maxSalary: null
    };
    this.hireDateRange = {
      startDate: null,
      endDate: null
    };

    this.selectedEmployee = null;
    this.dataTable.ajax.reload();
  }

  ngOnDestroy(): void {
    if (this.dataTable) {
      this.dataTable.destroy(true);
    }
  }

  // nextPage(): void {
  //   if (this.pageIndex + 1 < this.totalPages) {
  //     this.pageIndex++;
  //     this.loadEmployees();
  //   }
  // }

  // prevPage(): void {
  //   if (this.pageIndex > 0) {
  //     this.pageIndex--;
  //     this.loadEmployees();
  //   }
  // }
}
