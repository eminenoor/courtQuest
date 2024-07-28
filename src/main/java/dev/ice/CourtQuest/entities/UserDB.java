package dev.ice.CourtQuest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="user")
@Data
public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long user_id;
    private int age;
    private String birth_date; //XX.XX.XXXX
    private String department;
    private String email;
    private String first_name;
    private String gender;
    private String last_name;
    private String password;
    private Double rating;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    /*
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
     */

}
