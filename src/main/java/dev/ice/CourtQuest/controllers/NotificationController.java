package dev.ice.CourtQuest.controllers;

import dev.ice.CourtQuest.entities.Notification;
import dev.ice.CourtQuest.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public Notification createNotification(@RequestParam Long userId, @RequestParam String message, @RequestParam String type) {
        return notificationService.createNotification(userId, message, type);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @PutMapping("/{notificationId}/read")
    public Notification markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }
}
