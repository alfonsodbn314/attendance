import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AttendanceService } from './services/attendance.service';
import { Attendance } from './models/attendance';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  private attendanceService = inject(AttendanceService);

  attendanceList = signal<Attendance[]>([]);
  newAttendance: Partial<Attendance> = {
    employeeId: '',
    locationId: ''
  };

  ngOnInit() {
    this.loadAttendance();
  }

  loadAttendance() {
    this.attendanceService.getAll().subscribe({
      next: (data) => {
        this.attendanceList.set(data.reverse());
      },
      error: (err) => {
        console.error('Error al cargar historial desde el servidor:', err);
        if (err.status === 0) {
          console.error('No se pudo conectar con el servidor. ¿Está el backend corriendo y permite CORS para http://localhost:4200?');
        }
      }
    });
  }

  register() {
    if (!this.newAttendance.employeeId || !this.newAttendance.locationId) return;

    const payload = {
      ...this.newAttendance,
      checkInTime: new Date().toISOString()
    };

    this.attendanceService.register(payload).subscribe({
      next: () => {
        this.newAttendance = { employeeId: '', locationId: '' };
        this.loadAttendance();
      },
      error: (err) => alert('Error al registrar entrada')
    });
  }

  checkOut(id: string | undefined) {
    if (!id || !confirm('¿Confirmar salida?')) return;

    this.attendanceService.checkOut(id).subscribe({
      next: () => this.loadAttendance(),
      error: (err) => alert('Error al registrar salida')
    });
  }
}
