package com.cts.careNexus.Notification.controller;

import com.cts.careNexus.Notification.DTO.NotificationDTO;
import com.cts.careNexus.Notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for the Notification module.
 * Provides user notification inbox queries and status management endpoints (Read, Update, Delete).
 * Notification creation is automated in the background directly from database events.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    // GET /api/notifications/user/{userId} - Fetch auto-generated DB notifications for user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'NURSE', 'BILLING', 'PHARMACIST', 'COMPLIANCE', 'ADMIN')")
    public ResponseEntity<List<NotificationDTO>> getByUser(@PathVariable Long userId) {
        log.info("API call: GET /api/notifications/user/{}", userId);

        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    // GET /api/notifications/unread-count/{userId} - Fetch total unread notifications count for user
    @GetMapping("/unread-count/{userId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'NURSE', 'BILLING', 'PHARMACIST', 'COMPLIANCE', 'ADMIN')")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        log.info("API call: GET /api/notifications/unread-count/{}", userId);
        long count;
        if (userId == null || userId <= 0) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            count = notificationService.getUnreadCountByEmail(email);
        } else {
            count = notificationService.getUnreadCount(userId);
        }
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }

    // PATCH /api/notifications/{id}/read - Mark a notification as Read
    @PatchMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'NURSE', 'BILLING', 'PHARMACIST', 'COMPLIANCE', 'ADMIN')")
    public ResponseEntity<NotificationDTO> markRead(@PathVariable Long id) {
        log.info("API call: PATCH /api/notifications/{}/read", id);
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    // PATCH /api/notifications/{id}/dismiss - Mark a notification as Dismissed
    @PatchMapping("/{id}/dismiss")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'NURSE', 'BILLING', 'PHARMACIST', 'COMPLIANCE', 'ADMIN')")
    public ResponseEntity<NotificationDTO> dismissNotification(@PathVariable Long id) {
        log.info("API call: PATCH /api/notifications/{}/dismiss", id);
        return ResponseEntity.ok(notificationService.dismissNotification(id));
    }

    // DELETE /api/notifications/{id} - Delete notification record from database
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'NURSE', 'BILLING', 'PHARMACIST', 'COMPLIANCE', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("API call: DELETE /api/notifications/{}", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}

