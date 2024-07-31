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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_user_id")
    private UserDB ratedUser;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_user_id")
    private UserDB ratingUser;
     */

    private double ratingVolleyball;
    private double ratingFootball;
    private double ratingTennis;
    private double ratingBasketball;

    private int volleyballNo;
    private int footballNo;
    private int tennisNo;
    private int basketballNo;

    private double ratingVolleyballPersonal;
    private double ratingFootballPersonal;
    private double ratingTennisPersonal;
    private double ratingBasketballPersonal;

    // Getters and Setters
}