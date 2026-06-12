package com.cts.careNexus.analytics.controller;

import com.cts.careNexus.analytics.entity.ClinicalReport;
import com.cts.careNexus.analytics.service.AnalyticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/reports/generate")
    public ResponseEntity<ClinicalReport> triggerManualReport(@RequestBody Map<String, String> payload) {
        String scope = payload.getOrDefault("scope", "GENERAL_COMPLIANCE");
        log.info("REST API Endpoint hit: POST /api/reports/generate for context: {}", scope);
        return ResponseEntity.ok(analyticsService.generateManualReport(scope));
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ClinicalReport>> extractSavedReports() {
        log.info("REST API Endpoint hit: GET /api/reports log dump.");
        return ResponseEntity.ok(analyticsService.fetchAllSavedReports());
    }
}