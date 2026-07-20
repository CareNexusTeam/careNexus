package com.cts.careNexus.workflow_emr.entity;

import com.cts.careNexus.patientManagement.entities.Patient;
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


    @ManyToOne
    @JoinColumn(name = "ConsultationID", nullable = false)
    private Consultation consultation;


    @ManyToOne
    @JoinColumn(name = "PatientID", nullable = false)
    private Patient patient;

    @Column(name = "MedicationName")
    private String medicationName;

    @Column(name = "Dosage")
    private String dosage;

    @Column(name = "Frequency")
    private Integer frequency;

    @Column(name = "Duration")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private PrescriptionStatus status;

    public enum PrescriptionStatus {
        Issued,
        Dispensed,
        Cancelled
    }
}