package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Data;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentID")
    private Long appointmentID;

    @Column(name = "PatientID")
    private Integer patientID;

    @Column(name = "DoctorID")
    private Integer doctorID;

    @Column(name = "DepartmentID")
    private Integer departmentID;


    @Column(name = "`ScheduledDateTime`")
    private LocalDateTime scheduledDateTime;

    @Column(name = "Type")
    private String type;

    @Column(name = "Status")
    private String status;
}
