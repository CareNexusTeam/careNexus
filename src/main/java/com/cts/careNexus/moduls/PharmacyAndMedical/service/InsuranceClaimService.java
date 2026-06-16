package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.service;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.ClaimStatus;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.InsuranceClaim;

import java.util.List;

public interface InsuranceClaimService {
    public InsuranceClaim createClaim(InsuranceClaim claim);
    public List<InsuranceClaim> getAllClaims();
    public InsuranceClaim getClaimById(Long id);
    public InsuranceClaim updateStatus(Long id, ClaimStatus status);
    public InsuranceClaim submitClaim(Long id);
}
