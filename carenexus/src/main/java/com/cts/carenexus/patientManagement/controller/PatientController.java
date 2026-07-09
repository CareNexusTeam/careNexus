package com.cts.carenexus.patientManagement.controller;

import com.cts.carenexus.patientManagement.dto.PatientDto;
import com.cts.carenexus.patientManagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patientDto) { // 🔥 Entity ki jagah DTO use kiya
        return ResponseEntity.ok(patientService.createPatient(patientDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @PatchMapping("/{patientId}/status")
    public ResponseEntity<PatientDto> updatePatientStatus(@RequestBody PatientDto patientDto, @PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.updatePatientStatus(patientDto, patientId));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> hardDeletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok("Patient record permanently deleted from system.");
    }
}