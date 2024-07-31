package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.repos.ActivityRepository;
import dev.ice.CourtQuest.repos.UserRepository;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.entities.Request;
import dev.ice.CourtQuest.repos.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RequestRepository requestRepository;

    @Transactional
    public void addParticipant(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        UserDB user = userRepository.findById(userId).orElse(null);
        if (activity != null && user != null) {
            if (!activity.getParticipants().contains(user)) {
                activity.addParticipant(user);
            }
        }
    }

    public Activity createActivity(String name, String status, String place, String date, String time, int quota) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setStatus(status);
        activity.setPlace(place);
        activity.setDate(date);
        activity.setTime(time);
        activity.setQuota(quota);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userRepository.findByEmailWithActivities(username);
        if (currentUser != null) {
            activity.addParticipant(currentUser);
            activity.setCreator(currentUser);
            return activityRepository.save(activity);
        }
        return null;
    }

    @Transactional(readOnly = true)
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
        UserDB user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Set<Activity> arr = user.getActivities();
            List<Activity> activities = new ArrayList<>(arr);
            return activities;
        }
        return null;
    }

    public List<Activity> getMyActivities(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userRepository.findByEmailWithActivities(username);
        Set<Activity> activities = currentUser.getActivities();
        List<Activity> activitiesList = new ArrayList<>(activities);
        return activitiesList;
    }

    public List<Activity> getMyFinishedActivities() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDB currentUser = userRepository.findByEmailWithActivities(username);
        Set<Activity> activities = currentUser.getActivities();
        List<Activity> finishedActivitiesList = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.isFinished()) {
                finishedActivitiesList.add(activity);
            }
        }
        return finishedActivitiesList;
    }

    public Activity joinActivity(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        UserDB user = userRepository.findById(userId).orElse(null);
        if (activity != null && user != null) {
            if (!activity.getParticipants().contains(user)) {
                activity.addParticipant(user);
            }
            return activity;
        }
        return null;
    }

    public void acceptUser(Activity activity, UserDB user) {
        activity.addParticipant(user);
        user.addActivity(activity);
    }

    public List<Activity> getPublicActivities() {
        return activityRepository.findByStatus("Public");
    }

    @Transactional
    public void removeUserFromActivity(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        UserDB user = userRepository.findById(userId).orElse(null);
        if (activity != null && user != null) {
            activity.getParticipants().remove(user);
            user.getActivities().remove(activity);
        }
    }

    public List<UserDB> getUsersByActivityId(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity != null) {
            Set<UserDB> participants = activity.getParticipants();
            return new ArrayList<>(participants);
        }
        return new ArrayList<>();
    }

    public void declineUser(Activity activity, UserDB player) {
        activity.getParticipants().remove(player);
    }

    public void sendJoinRequestNotification(Activity activity, UserDB sender) {
        notificationService.createNotification(
                activity.getCreator().getUser_id(),
                "You have a new request to join your activity",
                "request",
                activity.getName(),
                activity.getDate(),
                activity.getTime(),
                activity.getPlace()
        );
    }
}
