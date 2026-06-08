package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
@Entity
@Table(name = "DoctorSchedule")
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduleID")
    private Long scheduleID;

    @Column(name = "DoctorID")
    private Integer doctorID;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "StartTime")
    private LocalTime startTime;

    @Column(name = "EndTime")
    private LocalTime endTime;

    @Column(name = "SlotDurationMinutes")
    private Integer slotDurationMinutes;

    @Column(name = "AvailableSlots")
    private Integer availableSlots;
}