package com.cts.careNexus.moduls.Notification.repository;

import com.cts.careNexus.moduls.Notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserUserId(Long userId);
    List<Notification> findByUserUserIdAndStatus(Long userId, Notification.NotificationStatus status);
    List<Notification> findByCategory(Notification.NotificationCategory category);
}
