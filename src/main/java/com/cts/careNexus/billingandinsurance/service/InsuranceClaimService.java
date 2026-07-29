package com.cts.careNexus.billingandinsurance.service;

import com.cts.careNexus.billingandinsurance.dto.InsuranceClaimDTO;
import com.cts.careNexus.billingandinsurance.enums.ClaimStatus;

import java.util.List;

public interface InsuranceClaimService {

    InsuranceClaimDTO createClaim(InsuranceClaimDTO claim);
    List<InsuranceClaimDTO> getAllClaims();
    InsuranceClaimDTO getClaimById(Long id);
    InsuranceClaimDTO updateStatus(Long id, ClaimStatus status);
    InsuranceClaimDTO submitClaim(Long id);
}