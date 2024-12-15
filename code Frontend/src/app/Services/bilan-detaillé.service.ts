import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BilanDetailleService {
  private apiUrl = 'http://localhost:8080/api'; 

  constructor(private http: HttpClient) {}

  generateBilanDetaille(matricule: string): Observable<any> {
    const url = `${this.apiUrl}/bilan_detaille?matricule=${matricule}`;
    return this.http.get<any>(url).pipe(
      catchError((error: any) => {
        console.error('Error in generateBilanDetaille:', error);
        return throwError(error);
      })
    );
  }
}  