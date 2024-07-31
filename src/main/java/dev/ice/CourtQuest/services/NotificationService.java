package dev.ice.CourtQuest.services;


import dev.ice.CourtQuest.entities.Notification;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.NotificationRepository;
import dev.ice.CourtQuest.repos.UserRepository;
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

    public Notification createNotification(Long userId, String message, String type, String activityName, String activityDate, String activityTime, String activityPlace) {
        UserDB user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setType(type);
            notification.setActivityName(activityName);
            notification.setActivityDate(activityDate);
            notification.setActivityTime(activityTime);
            notification.setActivityPlace(activityPlace);
            notification.setCreatedDate(new Date());
            return notificationRepository.save(notification);
        }
        return null;
    }

    public List<Notification> getUserNotifications(Long userId) {
        UserDB user = userRepository.findById(userId).orElse(null);
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
