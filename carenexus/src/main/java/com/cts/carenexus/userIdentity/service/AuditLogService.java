package com.cts.carenexus.userIdentity.service;

import com.cts.carenexus.userIdentity.entities.AuditLog;
import com.cts.carenexus.userIdentity.entities.User;
import java.util.List;

public interface AuditLogService {
    void logAction(User user, String action, String details);
    List<AuditLog> getLogsByUser(Long userId);
}