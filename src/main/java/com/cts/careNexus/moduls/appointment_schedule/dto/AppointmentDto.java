package com.cts.careNexus.moduls.appointment_schedule.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    private Long appointmentID;
    private Integer patientID;
    private Integer doctorID;
    private Integer departmentID;
    private LocalDateTime scheduledDateTime;
    private String type;
    private String status;
}