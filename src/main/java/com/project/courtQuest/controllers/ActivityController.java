package com.project.courtQuest.controllers;

import com.project.courtQuest.entities.Activity;
import com.project.courtQuest.services.ActivityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public Activity createActivity(@RequestParam Long userId, @RequestBody Activity activity) {
        return activityService.createActivity(userId, activity);
    }

    @GetMapping("/{activityId}")
    public Activity getActivity(@PathVariable Long activityId) {
        return activityService.getActivity(activityId);
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PutMapping("/{activityId}")
    public Activity updateActivity(@PathVariable Long activityId, @RequestBody Activity activityDetails) {
        return activityService.updateActivity(activityId, activityDetails);
    }

    @GetMapping("/user/{userId}")
    public List<Activity> getUserActivities(@PathVariable Long userId) {
        return activityService.getUserActivities(userId);
    }

    @PostMapping("/{activityId}/join")
    public Activity joinActivity(@PathVariable Long activityId, @RequestParam Long userId) {
        return activityService.joinActivity(activityId, userId);
    }
}
