package com.cts.careNexus.userIdentity.controller;

import com.cts.careNexus.userIdentity.entities.AuditLog;
import com.cts.careNexus.userIdentity.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for accessing system audit logs, restricted to compliance and administration personnel.
@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    // Handles HTTP GET requests to fetch chronological audit logs for a specific user ID; restricted to Admins.
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditLog>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUser(userId));
    }
}