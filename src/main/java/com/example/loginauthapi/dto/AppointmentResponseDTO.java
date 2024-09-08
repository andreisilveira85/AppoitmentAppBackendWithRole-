package com.example.loginauthapi.dto;

import com.example.loginauthapi.domain.appointment.Appointment;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(String id, LocalDateTime dateTime, String reason) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getDateTime(), appointment.getReason());
    }
}
