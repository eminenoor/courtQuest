package com.project.courtQuest.services;

import com.project.courtQuest.entities.Activity;
import com.project.courtQuest.entities.User;
import com.project.courtQuest.repos.ActivityRepository;
import com.project.courtQuest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public Activity createActivity(Long userId, Activity activity) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            activity.setCreator(user);
            return activityRepository.save(activity);
        }
        return null;
    }

    public Activity getActivity(Long activityId) {
        return activityRepository.findById(activityId).orElse(null);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity updateActivity(Long activityId, Activity activityDetails) {
        return activityRepository.findById(activityId).map(activity -> {
            activity.setName(activityDetails.getName());
            activity.setStatus(activityDetails.getStatus());
            activity.setPlace(activityDetails.getPlace());
            activity.setDate(activityDetails.getDate());
            activity.setTime(activityDetails.getTime());
            activity.setQuota(activityDetails.getQuota());
            return activityRepository.save(activity);
        }).orElse(null);
    }

    public List<Activity> getUserActivities(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return activityRepository.findByCreator(user);
        }
        return null;
    }

    public Activity joinActivity(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (activity != null && user != null) {
            activity.getParticipants().add(user);
            user.getActivities().add(activity);
            userRepository.save(user);
            return activityRepository.save(activity);
        }
        return null;
    }
}
