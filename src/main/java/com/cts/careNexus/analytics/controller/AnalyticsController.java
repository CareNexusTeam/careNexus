package com.cts.careNexus.analytics.controller;

import com.cts.careNexus.analytics.entity.ClinicalReport;
import com.cts.careNexus.analytics.entity.ClinicalReport.ReportScope;
import com.cts.careNexus.analytics.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for the Analytics & Reporting module (Section 2.7 & 4.7).
 * Clinical reports are generated on-demand when calling the POST /api/analytics/reports/generate API,
 * avoiding unwanted background report creation.
 * Scope is strictly fixed to ReportScope enum (Department, Doctor, Period).
 */
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsController.class);

    @Autowired
    private AnalyticsService analyticsService;

    /**
     * POST /api/analytics/reports/generate - On-demand API to auto-generate and save a report from live DB metrics.
     * Accepts scope ("Department", "Doctor", "Period") and optional date range via query parameters or JSON body.
     */
    @PostMapping("/reports/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'COMPLIANCE')")
    public ResponseEntity<ClinicalReport> generateReport(
            @RequestParam(value = "scope", required = false) String scopeParam,
            @RequestParam(value = "startDate", required = false) String startDateParam,
            @RequestParam(value = "endDate", required = false) String endDateParam,
            @RequestBody(required = false) Map<String, String> body) {
        
        log.info("API call: POST /api/analytics/reports/generate");
        
        String scopeStr = scopeParam != null && !scopeParam.isBlank() 
                ? scopeParam 
                : (body != null ? body.getOrDefault("scope", "Period") : "Period");
                
        String startDateStr = startDateParam != null && !startDateParam.isBlank() 
                ? startDateParam 
                : (body != null ? body.get("startDate") : null);
                
        String endDateStr = endDateParam != null && !endDateParam.isBlank() 
                ? endDateParam 
                : (body != null ? body.get("endDate") : null);

        ReportScope scope = analyticsService.parseScope(scopeStr);
        ClinicalReport report = analyticsService.generateAndSaveReport(scope, startDateStr, endDateStr);
        return ResponseEntity.ok(report);
    }

    // GET /api/analytics/reports - List all generated clinical reports
    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'BILLING', 'COMPLIANCE')")
    public ResponseEntity<List<ClinicalReport>> getAll() {
        log.info("API call: GET /api/analytics/reports");
        return ResponseEntity.ok(analyticsService.getAllReports());
    }

    // GET /api/analytics/reports/{id} - Fetch specific report by ID
    @GetMapping("/reports/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'BILLING', 'COMPLIANCE')")
    public ResponseEntity<ClinicalReport> getById(@PathVariable Long id) {
        log.info("API call: GET /api/analytics/reports/{}", id);
        return ResponseEntity.ok(analyticsService.getReportById(id));
    }

    // GET /api/analytics/reports/scope/{scope} - Fetch reports filtered by ReportScope enum (Department, Doctor, Period)
    @GetMapping("/reports/scope/{scope}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'COMPLIANCE')")
    public ResponseEntity<List<ClinicalReport>> getByScope(@PathVariable String scope) {
        log.info("API call: GET /api/analytics/reports/scope/{}", scope);
        ReportScope reportScope = analyticsService.parseScope(scope);
        return ResponseEntity.ok(analyticsService.getByScope(reportScope));
    }

    // GET /api/analytics/patient-volume - Live patient volume calculated directly from DB
    @GetMapping("/patient-volume")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'COMPLIANCE')")
    public ResponseEntity<Map<String, Long>> getPatientVolume(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "departmentId", required = false) Long departmentId) {
        log.info("API call: GET /api/analytics/patient-volume");
        long volume = analyticsService.getPatientVolume(startDateStr, endDateStr, departmentId);
        return ResponseEntity.ok(Map.of("patientCount", volume));
    }

    // GET /api/analytics/revenue - Live financial revenue metrics from DB
    @GetMapping("/revenue")
    @PreAuthorize("hasAnyRole('ADMIN', 'BILLING', 'COMPLIANCE')")
    public ResponseEntity<Map<String, Double>> getRevenue(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/revenue");
        Map<String, Double> metrics = analyticsService.getRevenueMetrics(startDateStr, endDateStr);
        return ResponseEntity.ok(metrics);
    }

    // GET /api/analytics/appointments - Aggregate appointment statistics from DB
    @GetMapping("/appointments")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'COMPLIANCE')")
    public ResponseEntity<Map<String, Long>> getAppointments(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/appointments");
        Map<String, Long> stats = analyticsService.getAppointmentStats(startDateStr, endDateStr);
        return ResponseEntity.ok(stats);
    }

    // GET /api/analytics/prescriptions - Rank top prescribed medications from DB
    @GetMapping("/prescriptions")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PHARMACIST', 'COMPLIANCE')")
    public ResponseEntity<List<Map<String, Object>>> getPrescriptions(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        log.info("API call: GET /api/analytics/prescriptions");
        List<Map<String, Object>> list = analyticsService.getMostPrescribedMedications(limit);
        return ResponseEntity.ok(list);
    }

    // GET /api/analytics/department-performance - Aggregate department performance from DB
    @GetMapping("/department-performance")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'COMPLIANCE', 'BILLING')")
    public ResponseEntity<List<Map<String, Object>>> getDepartmentPerformance(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/department-performance");
        List<Map<String, Object>> list = analyticsService.getDepartmentPerformance(startDateStr, endDateStr);
        return ResponseEntity.ok(list);
    }

    // GET /api/analytics/summary - Get complete KPI summary metrics from DB
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'BILLING', 'COMPLIANCE')")
    public ResponseEntity<Map<String, Object>> getSummary(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/summary");
        Map<String, Object> summary = analyticsService.getAnalyticsSummary(startDateStr, endDateStr);
        return ResponseEntity.ok(summary);
    }
}

