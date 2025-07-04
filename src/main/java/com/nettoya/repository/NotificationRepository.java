package com.nettoya.repository;

import com.nettoya.model.entity.Notification;
import com.nettoya.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByFechaDesc(User user);
}

