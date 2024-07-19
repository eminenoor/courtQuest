package com.project.courtQuest.repos;


import com.project.courtQuest.entities.Notification;
import com.project.courtQuest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
}
