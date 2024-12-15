import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PointageServiceService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

 
  analyzePointageFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${this.apiUrl}/listPointage`, formData);
  }
  
}
