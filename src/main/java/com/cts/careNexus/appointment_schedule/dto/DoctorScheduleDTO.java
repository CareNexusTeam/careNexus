package com.cts.careNexus.appointment_schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorScheduleDTO {

    private Long scheduleId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Start Time is required")
    private LocalTime startTime;

    @NotNull(message = "End Time is required")
    private LocalTime endTime;

    @NotNull(message = "Slot Duration is required")
    private Integer slotDurationMinutes;

    @NotNull(message = "Available Slots is required")
    private Integer availableSlots;
}