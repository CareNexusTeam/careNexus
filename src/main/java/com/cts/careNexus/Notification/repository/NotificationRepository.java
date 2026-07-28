package com.cts.careNexus.Notification.repository;

import com.cts.careNexus.Notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for persisting and querying Notification entities.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Find all notifications for a given user ID
    List<Notification> findByUserUserId(Long userId);

    // Find notifications for a user filtered by status (Unread/Read/Dismissed)
    List<Notification> findByUserUserIdAndStatus(Long userId, Notification.NotificationStatus status);

    // Find notifications by category (Appointment/Clinical/Billing/Pharmacy/Compliance)
    List<Notification> findByCategory(Notification.NotificationCategory category);

    // Count unread notifications for a user
    long countByUserUserIdAndStatus(Long userId, Notification.NotificationStatus status);

    // Check if a notification already exists for a message and user to avoid duplicate alerts
    boolean existsByUserUserIdAndMessage(Long userId, String message);
}
