package com.cts.careNexus.workflow_emr.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationRequestDTO {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String symptoms;
    private String diagnosis;
    private String treatmentPlan;
    private LocalDateTime consultationDate;
    private String status;
}