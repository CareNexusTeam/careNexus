package com.cts.careNexus.Notification.repository;

import com.cts.careNexus.Notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Left completely empty for June 12 setup. Default database CRUD operations work automatically!
}