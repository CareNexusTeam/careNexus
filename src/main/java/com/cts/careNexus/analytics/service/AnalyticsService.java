package com.cts.careNexus.analytics.service;

import com.cts.careNexus.analytics.entity.ClinicalReport;
import com.cts.careNexus.analytics.repository.AnalyticsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    public ClinicalReport generateManualReport(String scope) {
        log.info("Triggering manual report generation for scope: {}", scope);

        Random random = new Random();
        int simulatedPatients = random.nextInt(200) + 50;
        double simulatedRevenue = 2500.0 + (5000.0 * random.nextDouble());

        // Corrected constructor parameter alignment with ID set to null
        ClinicalReport report = new ClinicalReport(null, scope, simulatedPatients, simulatedRevenue, LocalDateTime.now());

        return analyticsRepository.save(report);
    }

    public List<ClinicalReport> fetchAllSavedReports() {
        log.info("Extracting historical reporting rows directly out of database storage.");
        return analyticsRepository.findAll();
    }
}