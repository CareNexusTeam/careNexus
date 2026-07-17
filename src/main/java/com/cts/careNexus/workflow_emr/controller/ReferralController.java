package com.cts.careNexus.workflow_emr.controller;

import com.cts.careNexus.workflow_emr.dto.ReferralDTO;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.workflow_emr.service.ReferralService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    private final ReferralService referralService;

    public ReferralController(
            ReferralService referralService) {
        this.referralService = referralService;
    }

    @PostMapping
    public ResponseEntity<ReferralDTO> createReferral(
            @Valid @RequestBody ReferralDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(referralService.createReferral(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReferralDTO>> getAllReferrals() {

        return ResponseEntity.ok(
                referralService.getAllReferrals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReferralDTO> getReferralById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                referralService.getReferralById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Referral not found with id : " + id)));
    }

    @GetMapping("/consultation/{consultationID}")
    public ResponseEntity<List<ReferralDTO>>
    getByConsultationID(
            @PathVariable Integer consultationID) {

        return ResponseEntity.ok(
                referralService.getReferralsByConsultationId(
                        consultationID));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReferralDTO>>
    getByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                referralService.getReferralsByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<ReferralDTO>>
    getByPriority(
            @PathVariable String priority) {

        return ResponseEntity.ok(
                referralService.getReferralsByPriority(priority));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReferralDTO> updateReferral(
            @PathVariable Long id,
            @Valid @RequestBody ReferralDTO dto) {

        return ResponseEntity.ok(
                referralService.updateReferral(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReferral(
            @PathVariable Long id) {

        referralService.deleteReferral(id);

        return ResponseEntity.ok(
                "Referral deleted successfully");
    }
}