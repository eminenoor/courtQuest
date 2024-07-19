package com.project.courtQuest.services;

import com.project.courtQuest.entities.Activity;
import com.project.courtQuest.entities.Invitation;
import com.project.courtQuest.entities.User;
import com.project.courtQuest.repos.ActivityRepository;
import com.project.courtQuest.repos.InvitationRepository;
import com.project.courtQuest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private NotificationService notificationService;

    public Invitation sendInvitation(Long senderId, Long recipientId, Long activityId) {
        User sender = userRepository.findById(senderId).orElse(null);
        User recipient = userRepository.findById(recipientId).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (sender != null && recipient != null && activity != null) {
            Invitation invitation = new Invitation();
            invitation.setSender(sender);
            invitation.setRecipient(recipient);
            invitation.setActivity(activity);
            invitation.setStatus("Pending");
            invitationRepository.save(invitation);

            notificationService.createNotification(recipientId, "You have a new invitation from " + sender.getFirstName(), "INVITATION");

            return invitation;
        }
        return null;
    }

    public List<Invitation> getUserInvitations(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return invitationRepository.findByRecipient(user);
        }
        return null;
    }

    public Invitation respondToInvitation(Long invitationId, String status) {
        return invitationRepository.findById(invitationId).map(invitation -> {
            invitation.setStatus(status);
            invitationRepository.save(invitation);

            notificationService.createNotification(invitation.getSender().getUserId(), "Your invitation has been " + status.toLowerCase(), "INVITATION_RESPONSE");

            return invitation;
        }).orElse(null);
    }
}
