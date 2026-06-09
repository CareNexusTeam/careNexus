package com.cts.careNexus.controller;

import com.cts.careNexus.entity.Appointment;
import com.cts.careNexus.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // 1. Create / Book a new appointment (POST)

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }


    // 2. Retrieve all appointments (GET)
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // 3. Retrieve a specific appointment by its ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") Long id) {
        Optional<Appointment> appointmentData = appointmentRepository.findById(id);

        if (appointmentData.isPresent()) {
            return new ResponseEntity<>(appointmentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 4. Update an existing appointment details by ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") Long id, @RequestBody Appointment appointmentDetails) {
        Optional<Appointment> appointmentData = appointmentRepository.findById(id);

        if (appointmentData.isPresent()) {
            Appointment existingAppointment = appointmentData.get();

            // Lombok's @Data annotation automatically provides these getters and setters
            existingAppointment.setPatientID(appointmentDetails.getPatientID());
            existingAppointment.setDoctorID(appointmentDetails.getDoctorID());
            existingAppointment.setDepartmentID(appointmentDetails.getDepartmentID());
            existingAppointment.setScheduledDateTime(appointmentDetails.getScheduledDateTime());
            existingAppointment.setType(appointmentDetails.getType());
            existingAppointment.setStatus(appointmentDetails.getStatus());

            Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Delete / Cancel an appointment by ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") Long id) {
        try {
            if (appointmentRepository.existsById(id)) {
                appointmentRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}