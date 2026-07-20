package com.cts.careNexus.patientManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "MedicalHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "History_ID")
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Patient_ID", nullable = false)
    private Patient patient;

    @Column(name = "medical_condition")
    private String condition;

    @Column(name = "Diagnosed_Date")
    private LocalDate diagnosedDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private MedicalStatus status;

    public void setConditionName(String conditionName) {
        this.condition = conditionName;
    }
}