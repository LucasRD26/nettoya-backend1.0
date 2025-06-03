package com.nettoya.service.impl;

import com.nettoya.model.dto.response.NotificationResponse;
import com.nettoya.model.entity.Notification;
import com.nettoya.model.entity.User;
import com.nettoya.repository.NotificationRepository;
import com.nettoya.service.NotificationService;
import com.nettoya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl extends NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getUserNotifications() {
        User user = userService.getCurrentUserEntity();
        return notificationRepository.findByUserOrderByFechaDesc(user).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
            .id(notification.getId())
            .mensaje(notification.getMensaje())
            .leida(notification.getLeida())
            .fecha(notification.getFecha())
            .build();
    }
}

