package dev.ice.CourtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    @Getter
    String message;
    @Getter
    @Setter
    String type; // e.g., "INVITATION", "ACTIVITY_UPDATE", "RATING", etc.
    @Getter
    @Setter
    Boolean isRead = false;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    UserDB user;

    @Setter
    @Getter
    String activityName;
    @Getter
    @Setter
    String activityDate;
    @Setter
    @Getter
    String activityTime;
    @Setter
    @Getter
    String activityPlace;


    @Getter
    @Setter
    Date createdDate;

}
