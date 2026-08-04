package com.cts.careNexus.analytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity representing clinical analytics reports and system metrics.
 * Scope uses ReportScope enum directly (Department, Doctor, Period).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clinical_report")
public class ClinicalReport {

    // Primary key for the clinical report
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    // Scope enum mapped directly to string in DB (Department, Doctor, Period)
    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false, length = 50)
    private ReportScope scope = ReportScope.Period;

    // Total patient count calculated within the report scope
    @Column(name = "patient_count")
    private Integer patientCount;

    // Percentage of bed occupancy during the reported period
    @Column(name = "bed_occupancy")
    private Integer bedOccupancy;

    // Average duration (in minutes) spent per consultation
    @Column(name = "avg_consultation_time")
    private Double avgConsultationTime;

    // Total revenue collected from completed patient services/invoices
    @Column(name = "revenue_collected")
    private Double revenueCollected;

    // Timestamp when this clinical report was generated
    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;

    @PrePersist
    public void prePersist() {
        if (generatedDate == null) {
            generatedDate = LocalDateTime.now();
        }
        if (scope == null) {
            scope = ReportScope.Period;
        }
    }

    // Fixed report scopes as defined in section 2.7 & 4.7 of PDF documentation
    public enum ReportScope {
        Department, Doctor, Period
    }
}
