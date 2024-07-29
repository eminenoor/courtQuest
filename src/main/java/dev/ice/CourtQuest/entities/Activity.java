package dev.ice.CourtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="activity")
@Data
@EqualsAndHashCode(exclude = {"participants", "creator"})
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
    boolean finished = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_activities",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDB> participants = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private UserDB creator;

    /*
    @ManyToMany(mappedBy = "activities", fetch = FetchType.EAGER)
    private Set<UserDB> participants = new HashSet<>();

     */

    public void addParticipant(UserDB participant) {
        participants.add(participant);
        participant.addActivity(this);
    }
}
