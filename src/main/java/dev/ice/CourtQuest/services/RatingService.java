package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.Rating;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.RatingRepository;
import dev.ice.CourtQuest.repos.UserRepository;
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
        UserDB ratingUser = userRepository.findById(ratingUserId).orElse(null);
        UserDB ratedUser = userRepository.findById(ratedUserId).orElse(null);

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
        UserDB ratedUser = userRepository.findById(ratedUserId).orElse(null);
        if (ratedUser != null) {
            return ratingRepository.findByRatedUser(ratedUser);
        }
        return null;
    }
}
