package com.cts.careNexus.Notification.service;

import com.cts.careNexus.Notification.DTO.NotificationDTO;
import com.cts.careNexus.Notification.entity.Notification;
import com.cts.careNexus.Notification.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationDTO createNotification(NotificationDTO dto) {
        log.info("[SLF4J LOG] Processing notification creation sequence for User: {}", dto.getUserId());

        // Using Lombok's Builder engine to prevent missing parameters or constructor mismatch bugs
        Notification notification = Notification.builder()
                .userId(dto.getUserId())
                .message(dto.getMessage())
                .category(dto.getCategory() != null ? dto.getCategory() : "CLINICAL")
                .status("UNREAD")
                .createdDate(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);
        return mapToDTO(saved);
    }

    public List<NotificationDTO> getAllNotifications() {
        log.info("[SLF4J LOG] Extracting notification record row history from repository.");
        return notificationRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    private NotificationDTO mapToDTO(Notification entity) {
        return NotificationDTO.builder()
                .notificationId(entity.getNotificationId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .category(entity.getCategory())
                .status(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}