package com.nettoya.service;

import com.nettoya.model.dto.response.NotificationResponse;
import com.nettoya.model.entity.Notification;
import com.nettoya.model.entity.User;
import com.nettoya.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;

    public List<NotificationResponse> getUserNotifications() {
        User user = userService.getCurrentUser();
        return notificationRepository.findByUserOrderByFechaDesc(user).stream()
                .map(this::mapToNotificationResponse)
                .collect(Collectors.toList());
    }

    public void markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setLeida(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    private NotificationResponse mapToNotificationResponse(Notification notification) {
        NotificationResponse dto = new NotificationResponse();
        dto.setId(notification.getId());
        dto.setMensaje(notification.getMensaje());
        dto.setLeida(notification.getLeida());
        dto.setFecha(notification.getFecha());
        return dto;
    }
}

