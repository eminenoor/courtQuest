package com.project.courtQuest.repos;

import com.project.courtQuest.entities.Rating;
import com.project.courtQuest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRatedUser(User ratedUser);
}
