package com.cts.careNexus.moduls.analytics.repository;

import com.cts.careNexus.moduls.analytics.entity.ClinicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<ClinicalReport, Long> {
    List<ClinicalReport> findByScope(String scope);
}
