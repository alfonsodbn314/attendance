package com.attendance.infrastructure.adapter.input.rest.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String code,
        LocalDateTime timestamp
) {}
