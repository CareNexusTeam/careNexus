package com.cts.careNexus.patientManagement.controller;

import com.cts.careNexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.careNexus.patientManagement.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-histories")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    // Handles HTTP GET requests to fetch and return the complete list of medical history records for a specific patient ID.
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalHistoryDto>> getMedicalHistoryByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalHistoryService.getMedicalHistoryByPatient(patientId));
    }

    // Handles HTTP POST requests to create and associate a new medical history record with the specified patient ID.
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<String> addMedicalHistory(@PathVariable Long patientId, @RequestBody MedicalHistoryDto historyDto) {
        medicalHistoryService.addMedicalHistory(patientId, historyDto);
        return ResponseEntity.ok("Medical History added successfully for patient ID: " + patientId);
    }

    // Handles HTTP PATCH requests to update only the status field of a specific medical history record and return the updated DTO.
    @PatchMapping("/{historyId}/status")
    public ResponseEntity<MedicalHistoryDto> updateHistoryStatus(
            @PathVariable Long historyId,
            @RequestBody MedicalHistoryDto historyDto) {
        return ResponseEntity.ok(medicalHistoryService.updateHistoryStatus(historyId, historyDto.getStatus()));
    }
}