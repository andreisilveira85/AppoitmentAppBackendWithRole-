package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByPatientEmail(String patientEmail);
}



