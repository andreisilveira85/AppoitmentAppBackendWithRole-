package com.example.loginauthapi.domain.appointment;

import com.example.loginauthapi.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Buscar todas as consultas
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    // Buscar consultas por email do paciente
    public List<Appointment> findByPatientEmail(String patientEmail) {
        return appointmentRepository.findByPatientEmail(patientEmail);
    }

    // Criar uma nova consulta
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Deletar uma consulta por ID
    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}


