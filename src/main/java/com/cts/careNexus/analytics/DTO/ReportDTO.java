package com.cts.careNexus.analytics.DTO;

import java.time.LocalDateTime;

// Immutable DTO for transferring clinical report summaries across API layers
public record ReportDTO(
    Long reportId,         // Identifier of the report
    String scope,          // Department, Doctor, or Period scope
    Integer patientCount,  // Aggregate patient volume
    Double revenueCollected,// Revenue generated in period
    LocalDateTime generatedDate // Generation timestamp
) {}
