package com.cts.careNexus.workflow_emr.entity;

import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.appointment_schedule.entity.Appointment;
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

    @ManyToOne
    @JoinColumn(name = "AppointmentID", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "PatientID", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "DoctorID", nullable = false)
    private User doctor;

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

    public enum ConsultationStatus {
        InProgress,
        Completed
    }
}