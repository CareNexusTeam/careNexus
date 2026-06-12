package com.cts.careNexus.analytics.repository;

import com.cts.careNexus.analytics.entity.ClinicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<ClinicalReport, Long> {
    // Left completely empty for June 12 setup. Default CRUD runs automatically.
}