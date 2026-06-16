package com.cts.careNexus.moduls.Notification.service;

import com.cts.careNexus.moduls.Notification.entity.Notification;
import com.cts.careNexus.moduls.Notification.entity.Notification.NotificationStatus;
import com.cts.careNexus.moduls.Notification.repository.NotificationRepository;
import com.cts.careNexus.moduls.UserIdentity.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired private NotificationRepository notificationRepo;
    @Autowired private UserRepo userRepo;

    public Notification createNotification(Notification notification) {
        // Validate user exists
        Long userId = notification.getUser().getUserId();
        userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Message must not be empty
        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            throw new RuntimeException("Notification message cannot be empty.");
        }

        // Category must be provided
        if (notification.getCategory() == null) {
            throw new RuntimeException("Notification category is required.");
        }

        notification.setCreatedDate(LocalDateTime.now());
        notification.setStatus(NotificationStatus.Unread);
        log.info("Notification created for user {}: [{}] {}",
                userId, notification.getCategory(), notification.getMessage());
        return notificationRepo.save(notification);
    }

    public List<Notification> getByUserId(Long userId) {
        // Validate user exists
        userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return notificationRepo.findByUserUserId(userId);
    }

    public List<Notification> getUnreadByUserId(Long userId) {
        userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return notificationRepo.findByUserUserIdAndStatus(userId, NotificationStatus.Unread);
    }

    public Notification markAsRead(Long id) {
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));

        if (n.getStatus() == NotificationStatus.Dismissed) {
            throw new RuntimeException("Cannot mark a Dismissed notification as Read.");
        }

        n.setStatus(NotificationStatus.Read);
        log.info("Notification {} marked as Read.", id);
        return notificationRepo.save(n);
    }

    public Notification dismiss(Long id) {
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));

        n.setStatus(NotificationStatus.Dismissed);
        log.info("Notification {} dismissed.", id);
        return notificationRepo.save(n);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }

    public void deleteNotification(Long id) {
        if (!notificationRepo.existsById(id)) {
            throw new RuntimeException("Notification not found: " + id);
        }
        notificationRepo.deleteById(id);
        log.info("Notification {} deleted.", id);
    }
}
