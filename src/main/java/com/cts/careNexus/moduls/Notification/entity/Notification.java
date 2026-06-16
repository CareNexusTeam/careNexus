package com.cts.careNexus.moduls.Notification.entity;

import com.cts.careNexus.moduls.UserIdentity.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private NotificationCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private NotificationStatus status = NotificationStatus.Unread;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) createdDate = LocalDateTime.now();
    }

    public enum NotificationCategory {
        Appointment, Clinical, Billing, Pharmacy, Compliance
    }

    public enum NotificationStatus {
        Unread, Read, Dismissed
    }
}
