package com.cts.careNexus.Notification.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Column(name = "category", nullable = false)
    private String category; // e.g., APPOINTMENT, CLINICAL, BILLING

    @Column(name = "status", nullable = false)
    private String status; // e.g., UNREAD, READ

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}