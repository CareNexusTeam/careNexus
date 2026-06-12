package com.cts.careNexus.analytics.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "clinical_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "scope", nullable = false, length = 100)
    private String scope;

    @Column(name = "patient_count")
    private Integer patientCount;

    @Column(name = "revenue_collected")
    private Double revenueCollected;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;
}