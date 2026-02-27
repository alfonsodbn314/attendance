package com.attendance.infrastructure.adapter.input.rest.exception;

import com.attendance.domain.exception.AttendanceAlreadyActiveException;
import com.attendance.domain.exception.AttendanceNotFoundException;
import com.attendance.domain.exception.BusinessException;
import com.attendance.infrastructure.adapter.input.rest.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AttendanceAlreadyActiveException.class)
    public ResponseEntity<ErrorResponse> handleAttendanceAlreadyActiveException(AttendanceAlreadyActiveException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "ATTENDANCE_ALREADY_ACTIVE",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAttendanceNotFoundException(AttendanceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "ATTENDANCE_NOT_FOUND",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "BUSINESS_ERROR",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Ocurrió un error inesperado en el servidor.",
                "INTERNAL_SERVER_ERROR",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Ya existe una sesión activa para este empleado (Validación de Integridad)",
                "DUPLICATE_ATTENDANCE",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
    }
}
