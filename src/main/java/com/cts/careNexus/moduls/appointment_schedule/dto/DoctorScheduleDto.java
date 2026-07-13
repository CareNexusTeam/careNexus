package com.cts.careNexus.moduls.appointment_schedule.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorScheduleDto {

    private Long scheduleID;
    private Integer doctorID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotDurationMinutes;
    private Integer availableSlots;
}