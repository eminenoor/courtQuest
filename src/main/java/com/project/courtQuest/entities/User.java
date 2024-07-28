package com.project.courtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.common.value.qual.StringVal;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String firstName;
    String lastName;
    Date birthDate; //XX.XX.XXXX
    String department;
    String gender;
    String email;
    String password;
    Double rating;
    String verificationCode;
    int age;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Activity> createdActivities = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_activities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invitation> receivedInvitations = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invitation> sentInvitations = new HashSet<>();

    @OneToMany(mappedBy = "ratedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> receivedRatings = new HashSet<>();

    @OneToMany(mappedBy = "ratingUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> givenRatings = new HashSet<>();

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
