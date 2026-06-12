package com.cts.careNexus.analytics.DTO;

import java.time.LocalDateTime;


public record ReportDTO(Long reportId, String scope, Integer patientCount, Double revenueCollected, LocalDateTime generatedDate) {}