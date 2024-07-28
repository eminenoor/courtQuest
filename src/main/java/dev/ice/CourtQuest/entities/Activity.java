package dev.ice.CourtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="activity")
@Data
@EqualsAndHashCode(exclude = "participants")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long activityId;
    String name;
    String status; //public or private
    String place;
    String date;
    String time;
    int quota;

    @ManyToMany
    @JoinTable(
            name = "user_activities",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDB> participants = new HashSet<>();

    public void addParticipant(UserDB participant) {
        participants.add(participant);
        participant.addActivity(this);
    }
}
