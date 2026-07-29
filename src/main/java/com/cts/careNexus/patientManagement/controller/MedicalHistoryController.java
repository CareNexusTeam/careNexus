package com.cts.careNexus.patientManagement.controller;

import com.cts.careNexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.careNexus.patientManagement.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for managing clinical patient history records, enforced with strict medical authorization rules.
@RestController
@RequestMapping("/api/v1/medical-histories")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    // Handles HTTP GET requests to fetch medical history records for a patient; accessible to Doctors, Patients, and Admins.
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<List<MedicalHistoryDto>> getMedicalHistoryByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalHistoryService.getMedicalHistoryByPatient(patientId));
    }

    // Handles HTTP POST requests to create medical history entries; restricted strictly to Doctors.
    @PostMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<String> addMedicalHistory(@PathVariable Long patientId, @RequestBody MedicalHistoryDto historyDto) {
        medicalHistoryService.addMedicalHistory(patientId, historyDto);
        return ResponseEntity.ok("Medical History added successfully for patient ID: " + patientId);
    }

    // Handles HTTP PATCH requests to update medical history record status; restricted strictly to Doctors and Admins.
    @PatchMapping("/{historyId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<MedicalHistoryDto> updateHistoryStatus(
            @PathVariable Long historyId,
            @RequestBody MedicalHistoryDto historyDto) {
        return ResponseEntity.ok(medicalHistoryService.updateHistoryStatus(historyId, historyDto.getStatus()));
    }
}