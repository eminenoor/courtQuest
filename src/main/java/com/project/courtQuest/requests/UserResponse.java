package com.project.courtQuest.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Date birthDate;
    private String department;
    private Double rating;
    private List<Long> activityIds;
    private List<Long> createdActivityIds;
}

