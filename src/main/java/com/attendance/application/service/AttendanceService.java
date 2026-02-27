package com.attendance.application.service;

import com.attendance.application.port.in.CompleteAttendanceUseCase;
import com.attendance.application.port.in.GetAttendanceHistoryUseCase;
import com.attendance.application.port.in.RegisterAttendanceUseCase;
import com.attendance.application.port.out.AttendanceOutputPort;
import com.attendance.application.port.out.AttendancePersistencePort;
import com.attendance.domain.model.Attendance;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AttendanceService implements RegisterAttendanceUseCase, CompleteAttendanceUseCase, GetAttendanceHistoryUseCase {

    private final AttendancePersistencePort persistencePort;
    private final AttendanceOutputPort outputPort;

    // Inyección de dependencias por constructor
    public AttendanceService(AttendancePersistencePort persistencePort, AttendanceOutputPort outputPort) {
        this.persistencePort = persistencePort;
        this.outputPort = outputPort;
    }

    @Override
    public Attendance register(Attendance attendance) {
        // 1. Aquí a futuro pondremos reglas de negocio (ej. validar si ya tiene check-in)

        // 2. Guardamos en la base de datos usando el puerto de salida
        Attendance savedAttendance = persistencePort.save(attendance);

        // 3. Notificamos al Gateway externo (ficticio)
        outputPort.send(savedAttendance);

        // 4. Retornamos la entidad guardada (con su UUID generado) al controlador
        return savedAttendance;
    }

    @Override
    public Attendance execute(UUID id, LocalDateTime checkOutTime) {
        // 1. Buscar el registro existente
        Attendance attendance = persistencePort.findById(id.toString())
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found with id: " + id));

        // 2. Aplicar lógica de dominio para completar la salida
        Attendance updatedAttendance = attendance.checkOut();

        // 3. Guardar cambios
        return persistencePort.save(updatedAttendance);
    }

    @Override
    public List<Attendance> execute() {
        return persistencePort.findAll();
    }
}
