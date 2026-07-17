package com.cts.careNexus.userIdentity.service;

import com.cts.careNexus.userIdentity.entities.AuditLog;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepo auditLogRepository;

    // Creates, populates with current timestamp, and saves a new transaction-bounded audit log entity for a user action.
    @Override
    @Transactional
    public void logAction(User user, String action, String details) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setDetails(details);
        log.setTimeStamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    // Fetches and returns all audit log activities associated with a specific user ID, ordered chronologically descending.
    @Override
    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByUser(Long userId) {
        return auditLogRepository.findByUserUserIdOrderByTimeStampDesc(userId);
    }
}