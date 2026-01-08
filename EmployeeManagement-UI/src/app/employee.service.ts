import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8282/employee';

  constructor(private http: HttpClient) {}

  getEmployees(params: any) {
  return this.http.get<any>('http://localhost:8282/employee', {
    params
  });
}


//   getEmployees(page: number, size: number, filters: any) {

//   let params = new HttpParams()
//     .set('page', page.toString())
//     .set('size', size.toString());

//   if (filters.name && filters.name.trim() !== '') {
//     params = params.set('name', filters.name);
//   }

//   if (filters.status) {
//     params = params.set('status', filters.status);
//   }

//   if (filters.departmentId !== null && filters.departmentId !== undefined) {
//     params = params.set('departmentId', filters.departmentId.toString());
//   }

//   if (filters.minSalary !== null && filters.minSalary !== undefined) {
//     params = params.set('minSalary', filters.minSalary.toString());
//   }

//   if (filters.maxSalary !== null && filters.maxSalary !== undefined) {
//     params = params.set('maxSalary', filters.maxSalary.toString());
//   }

//   return this.http.get<any>(this.baseUrl, { params });
// }


  createEmployee(employee: any) {
    return this.http.post(this.baseUrl, employee);
  }

  getEmployeeById(id: number) {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  updateEmployee(id: number, employee: any) {
    return this.http.put(`${this.baseUrl}/${id}`, employee);
  }

  deleteEmployee(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
