import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8282/employee';

  constructor(private http: HttpClient) {}

  getEmployees(page: number, size: number, filters: any) {

  let params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString());

  if (filters.name) {
    params = params.set('name', filters.name);
  }

  if (filters.status) {
    params = params.set('status', filters.status);
  }

  if (filters.departmentId) {
    params = params.set('departmentId', filters.departmentId.toString());
  }

  if (filters.minSalary !== '') {
  params = params.set('minSalary', Number(filters.minSalary).toString());
  }

  if (filters.maxSalary !== '') {
  params = params.set('maxSalary', Number(filters.maxSalary).toString());
  }

  return this.http.get<any>(this.baseUrl, { params });
}

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
