package com.project.courtQuest.controllers;

import com.project.courtQuest.entities.User;
import com.project.courtQuest.repos.UserRepository;
import com.project.courtQuest.requests.*;
import com.project.courtQuest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
/*
    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        return userRepository.findByEmail(user.getEmail());
    }*/

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User newUser) {
        return userService.updateUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }

    private UserResponse convertToResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getBirthDate(),
                user.getDepartment(),
                user.getRating(),
                user.getActivities().stream().map(activity -> activity.getCreator().getUserId()).collect(Collectors.toList()),
                user.getCreatedActivities().stream().map(activity -> activity.getCreator().getUserId()).collect(Collectors.toList())
        );
    }

    private User convertToEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setAge(userRequest.getAge());
        user.setBirthDate(userRequest.getBirthDate());
        user.setDepartment(userRequest.getDepartment());
        user.setRating(userRequest.getRating());
        // Assume you have a method to fetch activities by IDs
        // user.setActivities(activityService.findActivitiesByIds(userRequest.getActivityIds()));
        // user.setCreatedActivities(activityService.findCreatedActivitiesByIds(userRequest.getCreatedActivityIds()));
        return user;
    }

/*
    @PostMapping("/verify-email")
    public String verifyEmail(@RequestBody VerifyEmailRequest request) {
        return userService.sendVerificationEmail(request.getEmail());
    }

    @PostMapping("/verify-code")
    public String verifyCode(@RequestBody VerifyCodeRequest request) {
        return userService.verifyCode(request.getEmail(), request.getCode());
    }
*/
}
