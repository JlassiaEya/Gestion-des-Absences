import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BilanGlobalService {

  private apiUrl = 'http://localhost:8080/api/bilanglobal'; 

  constructor(private http: HttpClient) {}

  genererBilanGlobal(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
