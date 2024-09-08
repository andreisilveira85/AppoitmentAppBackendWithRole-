package com.example.loginauthapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        LocalDateTime dateTime,

        @NotBlank
        String reason,

        @NotBlank
        @Email
        String patientEmail
) {

}
