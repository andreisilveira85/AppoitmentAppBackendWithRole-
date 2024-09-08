package com.example.loginauthapi.domain.appointment;

import com.example.loginauthapi.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByPatientEmail(String patientEmail) {
        return appointmentRepository.findByPatientEmail(patientEmail);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}


