package com.cts.careNexus.Notification.scheduler;

import com.cts.careNexus.Notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Background auto-scheduler component that periodically syncs and creates
 * DB notifications for new appointments, invoices, consultations, and prescriptions.
 * Automatically catches required data from DB as soon as it generates.
 */
@Component
public class NotificationAutoScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationAutoScheduler.class);

    @Autowired
    private NotificationService notificationService;

    /**
     * Periodically runs every 5 seconds with an initial delay of 2 seconds
     * to catch database updates instantly across all activity modules.
     */
    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    public void scheduleNotificationSync() {
        try {
            log.trace("Executing scheduled background notification synchronization from DB...");
            notificationService.syncAllNotifications();
        } catch (Exception e) {
            log.error("Error during scheduled notification synchronization loop: {}", e.getMessage(), e);
        }
    }
}
