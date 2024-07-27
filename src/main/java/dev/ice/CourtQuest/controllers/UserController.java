package dev.ice.CourtQuest.controllers;

import dev.ice.CourtQuest.entities.User;
import dev.ice.CourtQuest.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
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


    @GetMapping("/registration")
    public String getRegPage(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("message", "Submitted Successfully");
        return "register";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        List<User> listOfUsers = userService.getAllUsers();
        model.addAttribute("user", listOfUsers);
        return "user";
    }

}
