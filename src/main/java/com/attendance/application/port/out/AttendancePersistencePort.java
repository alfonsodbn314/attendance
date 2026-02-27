package com.attendance.application.port.out;

import com.attendance.domain.model.Attendance;


import java.util.List;
import java.util.Optional;

public interface AttendancePersistencePort {
    List<Attendance> findAll();
    Attendance save(Attendance reservation);
    Optional<Attendance> findById(String id);
    Optional<Attendance> findActiveByEmployeeId(String employeeId);
}
