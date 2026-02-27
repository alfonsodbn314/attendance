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
        if (checkInTime != null && checkOutTime != null && checkInTime.isAfter(checkOutTime)) {
            throw new IllegalArgumentException("Check-in time cannot be after check-out time");
        }
        if (status == null || status.isBlank()) {
            status = "PRESENT"; // Valor por defecto según el nuevo dominio
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
