package com.cts.careNexus.workflow_emr.controller;

import com.cts.careNexus.workflow_emr.dto.ConsultationDTO;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.workflow_emr.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(
            ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PostMapping
    public ResponseEntity<ConsultationDTO> createConsultation(
            @Valid @RequestBody ConsultationDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultationService.createConsultation(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {

        return ResponseEntity.ok(
                consultationService.getAllConsultations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDTO> getConsultationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                consultationService.getConsultationById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Consultation not found with id : "
                                                + id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDTO> updateConsultation(
            @PathVariable Long id,
            @Valid @RequestBody ConsultationDTO dto) {

        return ResponseEntity.ok(
                consultationService.updateConsultation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConsultation(
            @PathVariable Long id) {

        consultationService.deleteConsultation(id);

        return ResponseEntity.ok(
                "Consultation deleted successfully");
    }
}