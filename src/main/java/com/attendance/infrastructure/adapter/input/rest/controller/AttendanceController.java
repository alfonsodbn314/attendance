package com.attendance.infrastructure.adapter.input.rest.controller;

import com.attendance.application.port.in.CompleteAttendanceUseCase;
import com.attendance.application.port.in.GetAttendanceHistoryUseCase;
import com.attendance.application.port.in.RegisterAttendanceUseCase;
import com.attendance.infrastructure.adapter.input.rest.dto.AttendanceRequest;
import com.attendance.infrastructure.adapter.input.rest.dto.AttendanceResponse;
import com.attendance.infrastructure.adapter.input.rest.mapper.AttendanceRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attendance") // Corregido el typo a 'attendance'
@RequiredArgsConstructor
@Tag(name = "Control de Asistencia", description = "Gestión de entradas y salidas para Estudiantes, Administrativos y Catedráticos")
public class AttendanceController {

    private final RegisterAttendanceUseCase registerUseCase;
    private final CompleteAttendanceUseCase completeUseCase;
    private final GetAttendanceHistoryUseCase getHistoryUseCase;
    private final AttendanceRestMapper mapper;

    @PostMapping
    @Operation(
            summary = "Registro de Entrada (Check-in)",
            description = "Recibe los datos del empleado y la ubicación para registrar su entrada. Válido para Estudiantes, Administrativos y Catedráticos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asistencia registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar el registro")
    })
    public ResponseEntity<AttendanceResponse> create(@Valid @RequestBody AttendanceRequest request) {
        // 1. Mapear DTO de entrada a objeto de dominio (Corregido el typo de la variable)
        var attendanceDomain = mapper.toDomain(request);

        // 2. Ejecutar el caso de uso (Corregido de .execute() a .register())
        var savedAttendance = registerUseCase.register(attendanceDomain);

        // 3. Mapear resultado de dominio a DTO de salida y retornar 201
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toResponse(savedAttendance));
    }

    @PatchMapping("/{id}/checkout")
    @Operation(
            summary = "Registro de Salida (Check-out)",
            description = "Actualiza un registro existente con la fecha y hora de salida, requiriendo el ID generado en la entrada, cambiando el estado a 'COMPLETED'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salida registrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Registro de asistencia no encontrado"),
            @ApiResponse(responseCode = "400", description = "La fecha de salida es inválida (anterior a la entrada)")
    })
    public ResponseEntity<AttendanceResponse> checkout(
            @PathVariable UUID id,
            @RequestBody LocalDateTime checkOutTime) {

        var updatedAttendance = completeUseCase.execute(id, checkOutTime);
        return ResponseEntity.ok(mapper.toResponse(updatedAttendance));
    }

    @GetMapping
    @Operation(summary = "Historial Global de Asistencias", description = "Retorna un listado de todas las asistencias registradas en la base de datos.")
    public ResponseEntity<List<AttendanceResponse>> findAll() {
        var attendanceList = getHistoryUseCase.execute();
        return ResponseEntity.ok(mapper.toResponseList(attendanceList));
    }
}
