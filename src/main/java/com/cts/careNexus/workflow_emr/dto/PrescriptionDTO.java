package com.cts.careNexus.workflow_emr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrescriptionDTO {

    private Long prescriptionId;

    @NotNull(message = "Consultation Id is required")
    private Long consultationId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    private String medicationName;

    private String dosage;

    private Integer frequency;

    private Integer duration;

    @NotNull(message = "Status is required")
    private String status;
}