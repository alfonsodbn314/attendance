package com.attendance.domain.exception;

public class AttendanceNotFoundException extends BusinessException {
    public AttendanceNotFoundException(String id) {
        super("No se encontró el registro de asistencia con ID: " + id);
    }
}
