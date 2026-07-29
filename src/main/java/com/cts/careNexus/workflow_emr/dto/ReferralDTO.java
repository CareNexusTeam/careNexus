package com.cts.careNexus.workflow_emr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReferralDTO {

    private Long referralId;

    @NotNull(message = "Consultation Id is required")
    private Integer consultationID;

    @NotNull(message = "Referred Department is required")
    private String referredToDepartment;

    private String reason;

    @NotNull(message = "Priority is required")
    private String priority;

    @NotNull(message = "Status is required")
    private String status;
}