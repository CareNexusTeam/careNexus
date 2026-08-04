package com.cts.careNexus.analytics.repository;

import com.cts.careNexus.analytics.entity.ClinicalReport;
import com.cts.careNexus.analytics.entity.ClinicalReport.ReportScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository interface for persistence operations on ClinicalReport entities.
 */
@Repository
public interface AnalyticsRepository extends JpaRepository<ClinicalReport, Long> {
    // Find all clinical reports matching the given report scope enum (Department, Doctor, Period)
    List<ClinicalReport> findByScope(ReportScope scope);
}
