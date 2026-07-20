package com.cts.careNexus.appointment_schedule.entity;

import com.cts.careNexus.userIdentity.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DoctorSchedule")
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduleID")
    private Long scheduleID;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DoctorID")
    private User doctorID;

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