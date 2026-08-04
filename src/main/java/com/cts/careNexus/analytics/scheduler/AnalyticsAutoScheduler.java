package com.cts.careNexus.analytics.scheduler;

/**
 * Analytics report auto-scheduler disabled.
 * Clinical reports are generated strictly on-demand via the POST /api/analytics/reports/generate API endpoint
 * to avoid creating unwanted background reports.
 */
public class AnalyticsAutoScheduler {
    // Disabled: Report creation is triggered strictly via REST API request (POST /api/analytics/reports/generate)
}
