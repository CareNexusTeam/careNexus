package com.cts.careNexus.appointment_schedule.entity;

import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.userIdentity.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentID")
    private Long appointmentID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentID")
    private User departmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DoctorID")
    private User doctorID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patientID")
    private Patient patientID;

    @Column(name = "ScheduledDateTime")
    private LocalDateTime scheduledDateTime;

    @Column(name = "Type")
    private String type;

    @Column(name = "Status")
    private String status;
}
