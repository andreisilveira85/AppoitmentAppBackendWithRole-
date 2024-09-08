package com.example.loginauthapi.domain.appointment;

import com.example.loginauthapi.dto.AppointmentRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime dateTime;

    private String reason;

    private String patientEmail;

    public Appointment(AppointmentRequestDTO data){
        this.dateTime = data.dateTime();
        this.reason = data.reason();
        this.patientEmail = data.patientEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

