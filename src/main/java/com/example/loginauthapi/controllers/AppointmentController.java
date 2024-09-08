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
        // O principal contém o email do usuário autenticado
        String userEmail = principal.getName();

        // Busque as consultas no repositório com base no email do paciente autenticado
        List<AppointmentResponseDTO> appointments = appointmentRepository.findByPatientEmail(userEmail)
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{id}")
    public List<Appointment> getAppointmentsForPatient(@PathVariable String id, Authentication authentication) {
        // Supondo que o principal seja o email do paciente
        String email = authentication.getName(); // authentication.getName() geralmente retorna o nome de usuário ou email

        // Retorna as consultas associadas ao email do paciente autenticado
        return appointmentService.findByPatientEmail(email);
    }



    @PostMapping
    public ResponseEntity<?> postAppointment(@RequestBody @Valid AppointmentRequestDTO body) {
        Appointment newAppointment = new Appointment(body);

        appointmentRepository.save(newAppointment);
        return ResponseEntity.ok(new AppointmentResponseDTO(newAppointment));
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
