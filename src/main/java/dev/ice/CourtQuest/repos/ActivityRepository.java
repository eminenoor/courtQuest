package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByStatus(String status);
    //List<Activity> findByUser(UserDB user);
    /*
    @Query("SELECT a FROM Activity a LEFT JOIN FETCH a.participants WHERE a IN (SELECT act FROM Activity act JOIN act.participants p WHERE p.email = :email)")
    List<Activity> findByUsernameWithParticipants(String username);
     */
}
