package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.controller;


import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.ClaimStatus;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.InsuranceClaim;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.service.InsuranceClaimServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class InsuranceClaimController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InsuranceClaimServiceImpl claimService;







    @PostMapping
    public InsuranceClaim createClaim(@RequestBody InsuranceClaim claim) {

        log.info("API: Creating claim");

        return claimService.createClaim(claim);
    }

    @GetMapping
    public List<InsuranceClaim> getAllClaims() {

        log.info("API: Fetch all claims");

        return claimService.getAllClaims();
    }

    @GetMapping("/{claimId}")
    public InsuranceClaim getClaimById(@PathVariable Long claimId) {

        log.info("API: Fetch claim {}", claimId);

        return claimService.getClaimById(claimId);
    }

    @PatchMapping("/{claimId}/status")
    public InsuranceClaim updateStatus(
            @PathVariable Long claimId,
            @RequestParam ClaimStatus status) {

        log.info("API: Update claim status");

        return claimService.updateStatus(claimId, status);
    }

    @PostMapping("/{claimId}/submit")
    public InsuranceClaim submitClaim(@PathVariable Long claimId) {

        log.info("API: Submit claim {}", claimId);

        return claimService.submitClaim(claimId);
    }


}
