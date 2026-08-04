package com.cts.careNexus.analytics.DTO;

import com.cts.careNexus.analytics.entity.ClinicalReport.ReportScope;
import java.time.LocalDateTime;

/**
 * Immutable DTO for transferring clinical report summaries across API layers.
 * Scope uses ReportScope enum directly (Department, Doctor, Period).
 */
public record ReportDTO(
    Long reportId,              // Identifier of the report
    ReportScope scope,          // Fixed ReportScope enum (Department, Doctor, Period)
    Integer patientCount,       // Aggregate patient volume
    Integer bedOccupancy,       // Bed occupancy percentage
    Double avgConsultationTime, // Average consultation duration (mins)
    Double revenueCollected,    // Total revenue collected
    LocalDateTime generatedDate // Generation timestamp
) {}
