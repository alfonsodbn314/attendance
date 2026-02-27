package com.attendance.infrastructure.adapter.output.persistence.repository;

import com.attendance.infrastructure.adapter.output.persistence.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceJpaRepository extends JpaRepository<AttendanceEntity, UUID> {
    Optional<AttendanceEntity> findByEmployeeIdAndStatus(String employeeId, String status);
}
