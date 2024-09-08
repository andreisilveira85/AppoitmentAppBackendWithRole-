package com.example.loginauthapi.infra.security;

import com.example.loginauthapi.domain.appointment.Appointment;
import com.example.loginauthapi.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceSpecification {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }
}