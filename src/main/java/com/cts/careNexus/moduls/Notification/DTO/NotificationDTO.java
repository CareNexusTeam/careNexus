package com.cts.careNexus.moduls.Notification.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long notificationId;
    private Long userId;
    private String message;
    private String category;
    private String status;
    private LocalDateTime createdDate;
}
