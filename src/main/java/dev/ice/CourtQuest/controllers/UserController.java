package dev.ice.CourtQuest.controllers;

import dev.ice.CourtQuest.entities.Notification;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.services.NotificationService;
import dev.ice.CourtQuest.services.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private NotificationService notificationService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDB> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDB createUser(@RequestBody UserDB user) {
        return userService.saveUser(user);
    }
/*
    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        return userRepository.findByEmail(user.getEmail());
    }*/

    @GetMapping("/{user_id}")
    public UserDB getUser(@PathVariable Long user_id) {
        return userService.getUser(user_id);
    }

    @PutMapping("/{user_id}")
    public UserDB updateUser(@PathVariable Long user_id, @RequestBody UserDB newUser) {
        return userService.updateUser(user_id, newUser);
    }

    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable Long user_id) {
        userService.deleteById(user_id);
    }


    @GetMapping("/registration")
    public String getRegPage(@ModelAttribute("user") UserDB user) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDB user, Model model) {
        userService.saveUser(user);
        model.addAttribute("message", "Submitted Successfully");
        return "register";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        List<UserDB> listOfUsers = userService.getAllUsers();
        model.addAttribute("user", listOfUsers);
        return "user";
    }

    @GetMapping("/email")
    public UserDB findUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/{user_id}/notifications")
    @Transactional(readOnly = true)
    public List<Notification> getUserNotifications(@PathVariable Long user_id) {
        return notificationService.getUserNotifications(user_id);
    }
}