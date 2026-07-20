package com.cts.careNexus.appointment_schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private Long appointmentId;

    @NotNull(message = "Department Id is required")
    private Long departmentId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotNull(message = "Scheduled Date Time is required")
    private LocalDateTime scheduledDateTime;

    private String type;

    private String status;
}