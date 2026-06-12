package com.cts.careNexus.Notification.controller;

import com.cts.careNexus.Notification.DTO.NotificationDTO;
import com.cts.careNexus.Notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // POST: http://localhost:8081/api/notifications
    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> dispatchAlert(@RequestBody NotificationDTO payload) {
        log.info("REST API Endpoint hit: POST /api/notifications for User ID: {}", payload.getUserId());
        return ResponseEntity.ok(notificationService.createNotification(payload));
    }

    // GET: http://localhost:8081/api/notifications
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> browseNotificationsLog() {
        log.info("REST API Endpoint hit: GET /api/notifications log pull sequence.");
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
}