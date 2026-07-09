package com.cts.carenexus.userIdentity.service;

import com.cts.carenexus.userIdentity.entities.AuditLog;
import com.cts.carenexus.userIdentity.entities.User;
import com.cts.carenexus.userIdentity.repository.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepo auditLogRepository;

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

    @Override
    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByUser(Long userId) {
        return auditLogRepository.findByUserUserIdOrderByTimeStampDesc(userId);
    }
}