package com.cts.carenexus.patientManagement.controller;

import com.cts.carenexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.carenexus.patientManagement.entities.MedicalStatus;
import com.cts.carenexus.patientManagement.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-histories")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalHistoryDto>> getMedicalHistoryByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalHistoryService.getMedicalHistoryByPatient(patientId));
    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<String> addMedicalHistory(@PathVariable Long patientId, @RequestBody MedicalHistoryDto historyDto) {
        medicalHistoryService.addMedicalHistory(patientId, historyDto);
        return ResponseEntity.ok("Medical History added successfully for patient ID: " + patientId);
    }

    @PatchMapping("/{historyId}/status")
    public ResponseEntity<MedicalHistoryDto> updateHistoryStatus(
            @PathVariable Long historyId,
            @RequestBody MedicalHistoryDto historyDto) {
        return ResponseEntity.ok(medicalHistoryService.updateHistoryStatus(historyId, historyDto.getStatus()));
    }
}