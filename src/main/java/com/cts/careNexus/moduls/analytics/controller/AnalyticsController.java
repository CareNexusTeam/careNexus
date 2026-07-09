package com.cts.careNexus.moduls.analytics.controller;

import com.cts.careNexus.moduls.analytics.entity.ClinicalReport;
import com.cts.careNexus.moduls.analytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/report")
    public ResponseEntity<ClinicalReport> generate(@RequestBody ClinicalReport report) {
        return ResponseEntity.ok(analyticsService.generateReport(report));
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ClinicalReport>> getAll() {
        return ResponseEntity.ok(analyticsService.getAllReports());
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<ClinicalReport> getById(@PathVariable Long id) {
        return ResponseEntity.ok(analyticsService.getReportById(id));
    }

    @GetMapping("/reports/scope/{scope}")
    public ResponseEntity<List<ClinicalReport>> getByScope(@PathVariable String scope) {
        return ResponseEntity.ok(analyticsService.getByScope(scope));
    }
}
