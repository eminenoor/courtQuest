package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    //List<Activity> findByUser(UserDB user);
}
