package com.cts.careNexus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrescriptionID")
    private Long prescriptionID;

    @Column(name = "ConsultationID")
    private Integer consultationID;

    @Column(name = "PatientID")
    private Integer patientID;

    @Column(name = "MedicationName")
    private String medicationName;

    @Column(name = "Dosage")
    private String dosage;

    @Column(name = "Frequency")
    private String frequency;

    @Column(name = "Duration")
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private PrescriptionStatus status;

    // ENUM
    public enum PrescriptionStatus {
        Issued,
        Dispensed,
        Cancelled
    }
}