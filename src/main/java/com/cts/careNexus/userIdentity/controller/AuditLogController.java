package com.cts.careNexus.userIdentity.controller;

import com.cts.careNexus.userIdentity.entities.AuditLog;
import com.cts.careNexus.userIdentity.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    // Handles HTTP GET requests to fetch and return the chronological list of audit logs for a specific user ID.
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUser(userId));
    }
}