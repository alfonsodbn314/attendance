package com.attendance.infrastructure.adapter.output.gateway;

import com.attendance.application.port.out.AttendanceOutputPort;
import com.attendance.domain.model.Attendance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class AttendanceGatewayAdapter implements AttendanceOutputPort {
    private static final Logger log = LoggerFactory.getLogger(AttendanceGatewayAdapter.class);

    @Override
    public void send(Attendance attendance) {
        log.info("SIMULACIÓN: Notificación externa enviada para el empleado: {} en la ubicación: {}",
                attendance.employeeId(), attendance.locationId());
    }
}

