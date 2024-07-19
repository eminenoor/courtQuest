package com.project.courtQuest.services;

import com.project.courtQuest.entities.Rating;
import com.project.courtQuest.entities.User;
import com.project.courtQuest.repos.RatingRepository;
import com.project.courtQuest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public Rating rateUser(Long ratingUserId, Long ratedUserId, Double ratingValue) {
        User ratingUser = userRepository.findById(ratingUserId).orElse(null);
        User ratedUser = userRepository.findById(ratedUserId).orElse(null);

        if (ratingUser != null && ratedUser != null) {
            Rating rating = new Rating();
            rating.setRatingUser(ratingUser);
            rating.setRatedUser(ratedUser);
            rating.setRating(ratingValue);
            return ratingRepository.save(rating);
        }
        return null;
    }

    public List<Rating> getRatingsForUser(Long ratedUserId) {
        User ratedUser = userRepository.findById(ratedUserId).orElse(null);
        if (ratedUser != null) {
            return ratingRepository.findByRatedUser(ratedUser);
        }
        return null;
    }
}
