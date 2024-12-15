import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class AbsenceService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  listeabsS1(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listAbsenceS1`);
  }
  listeabsS2(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listAbsenceS2`);
  }
  listeabs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listAbsence`);
  }
}