package com.cts.careNexus.Notification.entity;

import com.cts.careNexus.userIdentity.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * JPA Entity representing user notifications and clinical alerts.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Entity
@Table(name = "notification")
public class Notification {

    // Primary key for the notification record
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    // Associated system user recipient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Notification message content (e.g. appointment reminder, drug expiry alert)
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    // Notification category enum (Appointment, Clinical, Billing, Pharmacy, Compliance)
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private NotificationCategory category;

    // Read/Unread/Dismissed status
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private NotificationStatus status = NotificationStatus.Unread;

    // Timestamp when notification was created
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // Pre-persist hook to ensure creation timestamp is populated
    @PrePersist
    public void prePersist() {
        if (createdDate == null) createdDate = LocalDateTime.now();
    }

    // Supported alert and notification categories
    public enum NotificationCategory {
        Appointment, Clinical, Billing, Pharmacy, Compliance
    }

    // Supported notification status states
    public enum NotificationStatus {
        Unread, Read, Dismissed
    }
}
