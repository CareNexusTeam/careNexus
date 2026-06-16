package com.carenexus.carenexus.model;

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
    @Column(name = "HistoryID")
    private int historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PatientID", referencedColumnName = "PatientID", nullable = false)
    private Patient patient;

    @Column(name = "ConditionName", length = 255)
    private String conditionName;

    @Column(name = "DiagnosedDate")
    private LocalDate diagnosedDate;

    @Column(name = "Status", length = 50)
    private String status;
}