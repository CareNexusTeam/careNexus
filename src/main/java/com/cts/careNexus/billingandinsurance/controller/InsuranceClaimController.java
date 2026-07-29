package com.cts.careNexus.billingandinsurance.controller;

import com.cts.careNexus.billingandinsurance.dto.InsuranceClaimDTO;
import com.cts.careNexus.billingandinsurance.enums.ClaimStatus;
import com.cts.careNexus.billingandinsurance.service.InsuranceClaimServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
@CrossOrigin(origins ="https://localhost:4200")

public class InsuranceClaimController {

    private final InsuranceClaimServiceImpl claimService;

    @PreAuthorize(("hasRole('Billing') or hasRole('Admin)"))
    @PostMapping
    public InsuranceClaimDTO createClaim(
            @RequestBody InsuranceClaimDTO claim) {

        System.out.println("API: Creating claim");

        return claimService.createClaim(claim);
    }

    @PreAuthorize(("hasRole('Billing') or hasRole('Admin)"))
    @GetMapping
    public List<InsuranceClaimDTO> getAllClaims() {

        System.out.println("API: Fetch all claims");

        return claimService.getAllClaims();
    }

    @PreAuthorize(("hasRole('Billing') or hasRole('Admin)"))
    @GetMapping("/{claimId}")
    public InsuranceClaimDTO getClaimById(
            @PathVariable Long claimId) {

        System.out.println("API: Fetch claim " + claimId);

        return claimService.getClaimById(claimId);
    }

    @PreAuthorize(("hasRole('Billing') or hasRole('Admin)"))
    @PatchMapping("/{claimId}/status")
    public InsuranceClaimDTO updateStatus(
            @PathVariable Long claimId,
            @RequestParam ClaimStatus status) {

        System.out.println("API: Update claim status");

        return claimService.updateStatus(claimId, status);
    }


    @PreAuthorize(("hasRole('Billing') or hasRole('Admin)"))
    @PostMapping("/{claimId}/submit")
    public InsuranceClaimDTO submitClaim(
            @PathVariable Long claimId) {

        System.out.println("API: Submit claim " + claimId);

        return claimService.submitClaim(claimId);
    }
}