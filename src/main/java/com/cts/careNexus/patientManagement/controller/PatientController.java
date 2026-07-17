package com.cts.careNexus.patientManagement.controller;

import com.cts.careNexus.patientManagement.dto.PatientDto;
import com.cts.careNexus.patientManagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Handles HTTP POST requests to accept a patient payload, register them via the service, and return the created record.
    @PostMapping("/create")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patientDto) { // 🔥 Entity ki jagah DTO use kiya
        return ResponseEntity.ok(patientService.createPatient(patientDto));
    }

    // Handles HTTP GET requests to fetch and return the complete list of all registered patients from the system.
    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // Handles HTTP GET requests to fetch and return the details of a specific patient using their path-provided ID.
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    // Handles HTTP PATCH requests to partially update specific patient fields (like status) by their ID and return the modified data.
    @PatchMapping("/{patientId}/status")
    public ResponseEntity<PatientDto> updatePatientStatus(@RequestBody PatientDto patientDto, @PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.updatePatientStatus(patientDto, patientId));
    }

    // Handles HTTP DELETE requests to permanently purge a specific patient's record from the database based on their ID.
    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> hardDeletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok("Patient record permanently deleted from system.");
    }
}