package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.entities.AuditLog;
import com.cts.careNexus.userIdentity.entities.User;
import java.util.List;

public interface AuditLogService {
    void logAction(User user, String action, String details);
    List<AuditLog> getLogsByUser(Long userId);
}