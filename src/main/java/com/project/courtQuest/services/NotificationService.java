package com.project.courtQuest.services;


import com.project.courtQuest.entities.Notification;
import com.project.courtQuest.entities.User;
import com.project.courtQuest.repos.NotificationRepository;
import com.project.courtQuest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Long userId, String message, String type) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setType(type);
            notification.setCreatedDate(new Date());
            return notificationRepository.save(notification);
        }
        return null;
    }

    public List<Notification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return notificationRepository.findByUser(user);
        }
        return null;
    }

    public Notification markAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(notification -> {
            notification.setIsRead(true);
            return notificationRepository.save(notification);
        }).orElse(null);
    }
}
