package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.appointment.Appointment;
import com.example.loginauthapi.domain.appointment.AppointmentService;
import com.example.loginauthapi.dto.AppointmentRequestDTO;
import com.example.loginauthapi.dto.AppointmentResponseDTO;
import com.example.loginauthapi.repositories.AppointmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/appointments")

public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

//    @GetMapping
//    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments() {
//        List<AppointmentResponseDTO> appointments = appointmentRepository.findAll()
//                .stream()
//                .map(AppointmentResponseDTO::new)
//                .toList();
//        return ResponseEntity.ok(appointments);
//    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(Principal principal) {
        String userEmail = principal.getName();
        List<AppointmentResponseDTO> appointments = appointmentRepository.findByPatientEmail(userEmail)
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{id}")
    public List<Appointment> getAppointmentsForPatient(@PathVariable String id, Authentication authentication) {
        String email = authentication.getName();
        return appointmentService.findByPatientEmail(email);
    }

    @PostMapping
    public ResponseEntity<?> postAppointment(@RequestBody @Valid AppointmentRequestDTO body) {
        Appointment newAppointment = new Appointment(body);

        appointmentRepository.save(newAppointment);
        return ResponseEntity.ok(new AppointmentResponseDTO(newAppointment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable String id, @RequestBody @Valid AppointmentRequestDTO updatedAppointment) {
        return appointmentRepository.findById(id)
                .map(existingAppointment -> {
                    existingAppointment.setDateTime(updatedAppointment.dateTime());
                    existingAppointment.setReason(updatedAppointment.reason());
                    existingAppointment.setPatientEmail(updatedAppointment.patientEmail());

                    appointmentRepository.save(existingAppointment);

                    return ResponseEntity.ok(new AppointmentResponseDTO(existingAppointment));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String id) {
        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointmentRepository.delete(appointment);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
