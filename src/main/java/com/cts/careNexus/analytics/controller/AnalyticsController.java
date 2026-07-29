package com.cts.careNexus.analytics.controller;

import com.cts.careNexus.analytics.entity.ClinicalReport;
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
 * Single REST controller for the Analytics & Reporting module.
 * Manages clinical report generation and metrics dashboards.
 */
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsController.class);

    @Autowired
    private AnalyticsService analyticsService;

    // POST /api/analytics/report - Save a clinical report (Requires Admin, Doctor, or Compliance role)
    @PostMapping("/report")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Compliance')")
    public ResponseEntity<ClinicalReport> generate(@RequestBody ClinicalReport report) {
        log.info("API call: POST /api/analytics/report");
        return ResponseEntity.ok(analyticsService.generateReport(report));
    }

    // POST /api/analytics/reports/generate - Generate automated report from DB (Requires Admin, Doctor, or Compliance role)
    @PostMapping("/reports/generate")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Compliance')")
    public ResponseEntity<ClinicalReport> generateReport(@RequestBody Map<String, String> request) {
        log.info("API call: POST /api/analytics/reports/generate");
        String scope = request != null ? request.getOrDefault("scope", "Period") : "Period";
        String startDateStr = request != null ? request.get("startDate") : null;
        String endDateStr = request != null ? request.get("endDate") : null;
        ClinicalReport report = analyticsService.generateAndSaveReport(scope, startDateStr, endDateStr);
        return ResponseEntity.ok(report);
    }

    // GET /api/analytics/reports - List all clinical reports (Requires Admin, Doctor, Nurse, Billing, or Compliance role)
    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Nurse', 'Billing', 'Compliance')")
    public ResponseEntity<List<ClinicalReport>> getAll() {
        log.info("API call: GET /api/analytics/reports");
        return ResponseEntity.ok(analyticsService.getAllReports());
    }

    // GET /api/analytics/reports/{id} - Get report by ID (Requires Admin, Doctor, Nurse, Billing, or Compliance role)
    @GetMapping("/reports/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Nurse', 'Billing', 'Compliance')")
    public ResponseEntity<ClinicalReport> getById(@PathVariable Long id) {
        log.info("API call: GET /api/analytics/reports/{}", id);
        return ResponseEntity.ok(analyticsService.getReportById(id));
    }

    // GET /api/analytics/reports/scope/{scope} - Get reports by scope (Requires Admin, Doctor, or Compliance role)
    @GetMapping("/reports/scope/{scope}")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Compliance')")
    public ResponseEntity<List<ClinicalReport>> getByScope(@PathVariable String scope) {
        log.info("API call: GET /api/analytics/reports/scope/{}", scope);
        return ResponseEntity.ok(analyticsService.getByScope(scope));
    }

    // GET /api/analytics/patient-volume - Calculate live patient volume from DB (Requires Admin, Doctor, Nurse, or Compliance role)
    @GetMapping("/patient-volume")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Nurse', 'Compliance')")
    public ResponseEntity<Map<String, Long>> getPatientVolume(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "departmentId", required = false) Long departmentId) {
        log.info("API call: GET /api/analytics/patient-volume");
        long volume = analyticsService.getPatientVolume(startDateStr, endDateStr, departmentId);
        return ResponseEntity.ok(Map.of("patientCount", volume));
    }

    // GET /api/analytics/revenue - Calculate financial revenue metrics from DB (Requires Admin, Billing, or Compliance role)
    @GetMapping("/revenue")
    @PreAuthorize("hasAnyRole('Admin', 'Billing', 'Compliance')")
    public ResponseEntity<Map<String, Double>> getRevenue(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/revenue");
        Map<String, Double> metrics = analyticsService.getRevenueMetrics(startDateStr, endDateStr);
        return ResponseEntity.ok(metrics);
    }

    // GET /api/analytics/appointments - Aggregate appointment status counts from DB (Requires Admin, Doctor, Nurse, or Compliance role)
    @GetMapping("/appointments")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Nurse', 'Compliance')")
    public ResponseEntity<Map<String, Long>> getAppointments(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/appointments");
        Map<String, Long> stats = analyticsService.getAppointmentStats(startDateStr, endDateStr);
        return ResponseEntity.ok(stats);
    }

    // GET /api/analytics/prescriptions - Rank top prescribed medications from DB (Requires Admin, Doctor, Pharmacist, or Compliance role)
    @GetMapping("/prescriptions")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Pharmacist', 'Compliance')")
    public ResponseEntity<List<Map<String, Object>>> getPrescriptions(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        log.info("API call: GET /api/analytics/prescriptions");
        List<Map<String, Object>> list = analyticsService.getMostPrescribedMedications(limit);
        return ResponseEntity.ok(list);
    }

    // GET /api/analytics/department-performance - Aggregate volume & revenue per department (Requires Admin, Doctor, Compliance, or Billing role)
    @GetMapping("/department-performance")
    @PreAuthorize("hasAnyRole('Admin', 'Doctor', 'Compliance', 'Billing')")
    public ResponseEntity<List<Map<String, Object>>> getDepartmentPerformance(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        log.info("API call: GET /api/analytics/department-performance");
        List<Map<String, Object>> list = analyticsService.getDepartmentPerformance(startDateStr, endDateStr);
        return ResponseEntity.ok(list);
    }
}
