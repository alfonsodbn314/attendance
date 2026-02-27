package com.attendance.infrastructure.adapter.input.rest.mapper;

import com.attendance.domain.model.Attendance;
import com.attendance.infrastructure.adapter.input.rest.dto.AttendanceRequest;
import com.attendance.infrastructure.adapter.input.rest.dto.AttendanceResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AttendanceRestMapper {

    public Attendance toDomain(AttendanceRequest request) {
        return new Attendance(
                null,
                request.employeeId(),
                request.locationId(),
                request.checkInTime(),
                null,
                "PRESENT"
        );
    }

    public AttendanceResponse toResponse(Attendance domain) {
        return new AttendanceResponse(
                domain.id() != null ? UUID.fromString(domain.id()) : null,
                domain.employeeId(),
                domain.locationId(),
                domain.checkInTime(),
                domain.checkOutTime(),
                domain.status()
        );
    }

    public List<AttendanceResponse> toResponseList(List<Attendance> domainList) {
        return domainList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
