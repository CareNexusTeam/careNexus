package com.cts.careNexus.Notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enables Spring task scheduling strictly within the Notification module domain.
 */
@Configuration
@EnableScheduling
public class NotificationSchedulerConfig {
}
