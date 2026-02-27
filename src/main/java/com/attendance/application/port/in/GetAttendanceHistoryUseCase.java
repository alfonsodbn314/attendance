package com.attendance.application.port.in;

import com.attendance.domain.model.Attendance;
import java.util.List;

public interface GetAttendanceHistoryUseCase {
    List<Attendance> execute();
}
