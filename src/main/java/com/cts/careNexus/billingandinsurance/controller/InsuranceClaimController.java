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

    @PreAuthorize("hasAnyRole('BILLING' , 'ADMIN')")
    @PostMapping
    public InsuranceClaimDTO createClaim(
            @RequestBody InsuranceClaimDTO claim) {

        System.out.println("API: Creating claim");

        return claimService.createClaim(claim);
    }

    @PreAuthorize("hasAnyRole('BILLING' , 'ADMIN')")
    @GetMapping
    public List<InsuranceClaimDTO> getAllClaims() {

        System.out.println("API: Fetch all claims");

        return claimService.getAllClaims();
    }

    @PreAuthorize("hasAnyRole('BILLING' , 'ADMIN')")
    @GetMapping("/{claimId}")
    public InsuranceClaimDTO getClaimById(
            @PathVariable Long claimId) {

        System.out.println("API: Fetch claim " + claimId);

        return claimService.getClaimById(claimId);
    }

    @PreAuthorize("hasAnyRole('BILLING' , 'ADMIN')")
    @PatchMapping("/{claimId}/status")
    public InsuranceClaimDTO updateStatus(
            @PathVariable Long claimId,
            @RequestParam ClaimStatus status) {

        System.out.println("API: Update claim status");

        return claimService.updateStatus(claimId, status);
    }


    @PreAuthorize("hasAnyRole('BILLING' , 'ADMIN')")
    @PostMapping("/{claimId}/submit")
    public InsuranceClaimDTO submitClaim(
            @PathVariable Long claimId) {

        System.out.println("API: Submit claim " + claimId);

        return claimService.submitClaim(claimId);
    }
}