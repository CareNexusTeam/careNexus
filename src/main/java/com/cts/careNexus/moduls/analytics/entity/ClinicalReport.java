package com.cts.careNexus.moduls.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clinical_report")
public class ClinicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "scope", nullable = false, length = 100)
    private String scope;  // e.g. "Department", "Doctor", "Period"

    @Column(name = "patient_count")
    private Integer patientCount;

    @Column(name = "bed_occupancy")
    private Integer bedOccupancy;

    @Column(name = "avg_consultation_time")
    private Double avgConsultationTime;

    @Column(name = "revenue_collected")
    private Double revenueCollected;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;
}
