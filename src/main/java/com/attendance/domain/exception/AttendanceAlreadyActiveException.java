package com.attendance.domain.exception;

public class AttendanceAlreadyActiveException extends BusinessException {
    public AttendanceAlreadyActiveException(String employeeId) {
        super("El empleado con ID: " + employeeId + " ya tiene una asistencia activa.");
    }
}
