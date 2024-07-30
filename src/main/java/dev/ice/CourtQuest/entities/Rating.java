package dev.ice.CourtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="rating")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_user_id")
    private UserDB ratedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_user_id")
    private UserDB ratingUser;

    private Double rating;

    // Getters and Setters
}