import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8282/employee';

  constructor(private http: HttpClient) {}

//   getEmployees(params: any) {
//   return this.http.get<any>('http://localhost:8282/employee', {
//     params
//   });
// }


  getEmployees(filters: any) {
    let params = new HttpParams();

    // Loop through the object keys to set params dynamically
    Object.keys(filters).forEach(key => {
      if (filters[key] !== null && filters[key] !== undefined && filters[key] !== '') {
        params = params.set(key, filters[key].toString());
     }
    });

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

  softDeleteEmployee(id:number){
    return this.http.delete(`${this.baseUrl}/soft/${id}`);
  }
}
