package com.cts.careNexus.Notification.controller;

import com.cts.careNexus.Notification.DTO.NotificationDTO;
import com.cts.careNexus.Notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Single REST Controller for the Notification module.
 * Provides user notification inbox queries and status management endpoints.
 */
@RestController
@RequestMapping("/api")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    // GET /api/users/{id}/notifications - Fetch auto-generated DB notifications for user (Requires authenticated role)
    @GetMapping("/users/{id}/notifications")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<List<NotificationDTO>> getByUserNotifications(@PathVariable("id") Long id) {
        log.info("API call: GET /api/users/{}/notifications", id);
        return ResponseEntity.ok(notificationService.getNotificationsByUser(id));
    }

    // GET /api/notifications/user/{userId} - Backwards compatible user notification retrieval endpoint
    @GetMapping("/notifications/user/{userId}")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<List<NotificationDTO>> getByUser(@PathVariable Long userId) {
        log.info("API call: GET /api/notifications/user/{}", userId);
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    // GET /api/notifications/unread-count/{userId} - Fetch total unread notifications count for user
    @GetMapping("/notifications/unread-count/{userId}")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        log.info("API call: GET /api/notifications/unread-count/{}", userId);
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }

    // PATCH /api/notifications/{id}/read - Mark a notification as Read
    @PatchMapping("/notifications/{id}/read")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<NotificationDTO> markReadPatch(@PathVariable Long id) {
        log.info("API call: PATCH /api/notifications/{}/read", id);
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    // PUT /api/notifications/{id}/read - Backwards compatible endpoint to mark notification as Read
    @PutMapping("/notifications/{id}/read")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<NotificationDTO> markRead(@PathVariable Long id) {
        log.info("API call: PUT /api/notifications/{}/read", id);
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    // PATCH /api/notifications/{id}/dismiss - Mark a notification as Dismissed
    @PatchMapping("/notifications/{id}/dismiss")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<NotificationDTO> dismissNotification(@PathVariable Long id) {
        log.info("API call: PATCH /api/notifications/{}/dismiss", id);
        return ResponseEntity.ok(notificationService.dismissNotification(id));
    }

    // DELETE /api/notifications/{id} - Delete notification record from database
    @DeleteMapping("/notifications/{id}")
    @PreAuthorize("hasAnyRole('Patient', 'Doctor', 'Nurse', 'Billing', 'Pharmacist', 'Compliance', 'Admin')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("API call: DELETE /api/notifications/{}", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
