import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Attendance } from '../models/attendance';

@Injectable({ providedIn: 'root' })
export class AttendanceService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/v1/attendance';

  getAll(): Observable<Attendance[]> {
    return this.http.get<Attendance[]>(this.API_URL);
  }

  register(attendance: Partial<Attendance>): Observable<Attendance> {
    return this.http.post<Attendance>(this.API_URL, attendance);
  }

  checkOut(id: string): Observable<Attendance> {
    const checkOutTime = new Date().toISOString();
    return this.http.patch<Attendance>(`${this.API_URL}/${id}/checkout`, checkOutTime, {
      headers: { 'Content-Type': 'application/json' }
    });
  }
}
