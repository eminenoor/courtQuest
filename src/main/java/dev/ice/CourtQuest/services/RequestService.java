package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Request;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.ActivityRepository;
import dev.ice.CourtQuest.repos.RequestRepository;
import dev.ice.CourtQuest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserService userService;

    public List<Request> getCurrentRequests() {
        UserDB user = userService.getCurrentUser();
        if (user != null) {
            return requestRepository.findByRecipient(user);
        }
        return null;
    }

    public void joinActivity(Long senderId, Long recipientId, Long activityId) {
        UserDB sender = userRepository.findById(senderId).orElse(null);
        UserDB recipient = userRepository.findById(recipientId).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (sender != null && recipient != null && activity != null) {
            Request request = new Request();
            request.setSender(sender);
            request.setRecipient(recipient);
            request.setActivity(activity);
            request.setStatus("Pending");
            recipient.addRequest(request);
            requestRepository.save(request);
        }
    }

}
