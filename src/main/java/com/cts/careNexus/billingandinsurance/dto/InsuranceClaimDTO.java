package com.cts.careNexus.billingandinsurance.dto;

import com.cts.careNexus.billingandinsurance.enums.ClaimStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsuranceClaimDTO {

    private Long claimID;
    private Long invoiceId;
    private Integer insuranceProviderId;
    private double claimAmount;
    private LocalDateTime submissionDate;
    private ClaimStatus status;
}