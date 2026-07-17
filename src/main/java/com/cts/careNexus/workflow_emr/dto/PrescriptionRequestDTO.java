package com.cts.careNexus.workflow_emr.dto;

import lombok.Data;

@Data
public class PrescriptionRequestDTO {

    private Long consultationId;
    private Long patientId;

    private String medicationName;
    private String dosage;
    private Integer frequency;  // string → convert to number
    private Integer duration;   // string → convert to number

    private String status;
}