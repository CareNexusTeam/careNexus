package com.cts.careNexus.workflow_emr.controller;

import com.cts.careNexus.workflow_emr.dto.PrescriptionDTO;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.workflow_emr.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(
            PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionDTO> createPrescription(
            @Valid @RequestBody PrescriptionDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prescriptionService.createPrescription(dto));
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {

        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Prescription not found with id : " + id)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionDTO>>
    getByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByPatientId(patientId));
    }

    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<PrescriptionDTO>>
    getByConsultation(
            @PathVariable Long consultationId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByConsultationId(
                        consultationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionDTO dto) {

        return ResponseEntity.ok(
                prescriptionService.updatePrescription(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(
            @PathVariable Long id) {

        prescriptionService.deletePrescription(id);

        return ResponseEntity.ok(
                "Prescription deleted successfully");
    }
}