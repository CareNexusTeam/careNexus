package com.cts.careNexus.appointment_schedule.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {

    private Long patientId;
    private Long doctorId;
    private Long departmentId;
    private LocalDateTime scheduledDateTime;
    private String type;
    private String status;
}
