package com.project.courtQuest.repos;

import com.project.courtQuest.entities.Activity;
import com.project.courtQuest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCreator(User creator);
}
