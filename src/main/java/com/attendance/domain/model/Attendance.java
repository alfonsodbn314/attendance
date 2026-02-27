package com.attendance.domain.model;

import java.time.LocalDateTime;

public record Attendance(
        String id,
        String employeeId,
        String locationId,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        String status
) {
    public Attendance {
        // --- 1. Guardianes de nulidad y vacío ---
        if (employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        if (locationId == null || locationId.isBlank()) {
            throw new IllegalArgumentException("Location ID cannot be null or empty");
        }

        // --- 2. Lógica de consistencia temporal ---
        if (checkInTime != null && checkOutTime != null && checkInTime.isAfter(checkOutTime)) {
            throw new IllegalArgumentException("Check-in time cannot be after check-out time");
        }

        // --- 3. Manejo de estado por defecto ---
        if (status == null || status.isBlank()) {
            status = "PRESENT";
        }
    }

    public Attendance checkOut() {
        return new Attendance(
                this.id,
                this.employeeId,
                this.locationId,
                this.checkInTime,
                LocalDateTime.now(),
                "COMPLETED"
        );
    }
}

