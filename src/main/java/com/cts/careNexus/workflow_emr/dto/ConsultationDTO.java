package com.cts.careNexus.workflow_emr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationDTO {

    private Long consultationId;

    @NotNull(message = "Appointment Id is required")
    private Long appointmentId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    private String symptoms;

    private String diagnosis;

    private String treatmentPlan;

    @NotNull(message = "Consultation Date is required")
    private LocalDateTime consultationDate;

    @NotNull(message = "Status is required")
    private String status;
}