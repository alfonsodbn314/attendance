package com.attendance.infrastructure.adapter.output.persistence;

import com.attendance.application.port.out.AttendancePersistencePort;
import com.attendance.domain.model.Attendance;
import com.attendance.infrastructure.adapter.output.persistence.entity.AttendanceEntity;
import com.attendance.infrastructure.adapter.output.persistence.repository.AttendanceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AttendancePersistenceAdapter implements AttendancePersistencePort {

    private final AttendanceJpaRepository repository;

    @Override
    public List<Attendance> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Attendance save(Attendance attendance) {
        AttendanceEntity entity = toEntity(attendance);
        AttendanceEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Attendance> findById(String id) {
        return repository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public Optional<Attendance> findActiveByEmployeeId(String employeeId) {
        return repository.findByEmployeeIdAndStatus(employeeId, "PRESENT")
                .map(this::toDomain);
    }

    private Attendance toDomain(AttendanceEntity e) {
        return new Attendance(
                e.getId() != null ? e.getId().toString() : null,
                e.getEmployeeId(),
                e.getLocationId(),
                e.getCheckInTime(),
                e.getCheckOutTime(),
                e.getStatus()
        );
    }

    private AttendanceEntity toEntity(Attendance d) {
        return AttendanceEntity.builder()
                .id(d.id() != null ? UUID.fromString(d.id()) : null)
                .employeeId(d.employeeId())
                .locationId(d.locationId())
                .checkInTime(d.checkInTime())
                .checkOutTime(d.checkOutTime())
                .status(d.status())
                .build();
    }
}
