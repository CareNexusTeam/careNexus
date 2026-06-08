package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConsultationID")
    private Long consultationID;

    @Column(name = "AppointmentID")
    private Integer appointmentID;

    @Column(name = "PatientID")
    private Integer patientID;

    @Column(name = "DoctorID")
    private Integer doctorID;

    @Column(name = "Symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "Diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "TreatmentPlan", columnDefinition = "TEXT")
    private String treatmentPlan;

    @Column(name = "ConsultationDate")
    private LocalDateTime consultationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private ConsultationStatus status;

    // ENUM for Status
    public enum ConsultationStatus {
        InProgress,
        Completed
    }
}
