import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RetardService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  listretard(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/check`);
  }
  listretardS1(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/checkS1`);
  }
  listretardS2(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/checkS2`);
  }
}
