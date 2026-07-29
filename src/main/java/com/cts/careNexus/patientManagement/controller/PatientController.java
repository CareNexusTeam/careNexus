package com.cts.careNexus.patientManagement.controller;

import com.cts.careNexus.patientManagement.dto.PatientDto;
import com.cts.careNexus.patientManagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for patient management operations with fine-grained role-based security filters.
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Handles HTTP POST requests to register a new patient; allowed for Receptionists and Admins.
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.createPatient(patientDto));
    }

    // Handles HTTP GET requests to retrieve all patient records; accessible to Doctors, Receptionists, and Admins.
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // Handles HTTP GET requests to fetch details of a specific patient; accessible to Doctors, Receptionists, Patients, and Admins.
    @GetMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST', 'PATIENT')")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    // Handles HTTP PATCH requests to update patient profile status; restricted to Receptionists and Admins.
    @PatchMapping("/{patientId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<PatientDto> updatePatientStatus(@RequestBody PatientDto patientDto, @PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.updatePatientStatus(patientDto, patientId));
    }

    // Handles HTTP DELETE requests to permanently purge a patient record; restricted strictly to Admins.
    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> hardDeletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok("Patient record permanently deleted from system.");
    }
}