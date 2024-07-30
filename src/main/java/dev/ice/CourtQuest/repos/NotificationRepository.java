package dev.ice.CourtQuest.repos;


import dev.ice.CourtQuest.entities.Notification;
import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(UserDB user);
}
