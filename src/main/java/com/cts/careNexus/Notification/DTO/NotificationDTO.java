package com.cts.careNexus.Notification.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for notification payload serialization and client requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long notificationId; // Unique notification ID
    private Long userId;         // Target user ID receiving the alert
    private String message;      // Alert message body text
    private String category;     // Category string (Appointment, Clinical, Billing, Pharmacy, Compliance)
    private String status;       // Notification status string (Unread, Read, Dismissed)
    private LocalDateTime createdDate; // Creation timestamp
}
