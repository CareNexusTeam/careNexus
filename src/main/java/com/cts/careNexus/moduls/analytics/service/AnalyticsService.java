package com.cts.careNexus.moduls.analytics.service;

import com.cts.careNexus.moduls.analytics.entity.ClinicalReport;
import com.cts.careNexus.moduls.analytics.repository.AnalyticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);

    @Autowired private AnalyticsRepository analyticsRepository;

    public ClinicalReport generateReport(ClinicalReport report) {
        // Scope must be provided
        if (report.getScope() == null || report.getScope().isBlank()) {
            throw new RuntimeException("Report scope is required (e.g. Department, Doctor, Period).");
        }

        // PatientCount cannot be negative
        if (report.getPatientCount() != null && report.getPatientCount() < 0) {
            throw new RuntimeException("Patient count cannot be negative.");
        }

        // BedOccupancy cannot be negative
        if (report.getBedOccupancy() != null && report.getBedOccupancy() < 0) {
            throw new RuntimeException("Bed occupancy cannot be negative.");
        }

        // Revenue cannot be negative
        if (report.getRevenueCollected() != null && report.getRevenueCollected() < 0) {
            throw new RuntimeException("Revenue collected cannot be negative.");
        }

        // AvgConsultationTime cannot be negative
        if (report.getAvgConsultationTime() != null && report.getAvgConsultationTime() < 0) {
            throw new RuntimeException("Average consultation time cannot be negative.");
        }

        report.setGeneratedDate(LocalDateTime.now());
        log.info("Generating report. Scope: {}, Patients: {}, Revenue: {}",
                report.getScope(), report.getPatientCount(), report.getRevenueCollected());
        return analyticsRepository.save(report);
    }

    public List<ClinicalReport> getAllReports() {
        return analyticsRepository.findAll();
    }

    public ClinicalReport getReportById(Long id) {
        return analyticsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));
    }

    public List<ClinicalReport> getByScope(String scope) {
        if (scope == null || scope.isBlank()) {
            throw new RuntimeException("Scope parameter is required.");
        }
        return analyticsRepository.findByScope(scope);
    }
}
