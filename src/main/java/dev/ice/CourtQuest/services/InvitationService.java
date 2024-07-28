package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Invitation;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.ActivityRepository;
import dev.ice.CourtQuest.repos.InvitationRepository;
import dev.ice.CourtQuest.repos.UserRepository;
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
        UserDB sender = userRepository.findById(senderId).orElse(null);
        UserDB recipient = userRepository.findById(recipientId).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (sender != null && recipient != null && activity != null) {
            Invitation invitation = new Invitation();
            invitation.setSender(sender);
            invitation.setRecipient(recipient);
            invitation.setActivity(activity);
            invitation.setStatus("Pending");
            invitationRepository.save(invitation);

            notificationService.createNotification(recipientId, "You have a new invitation from " + sender.getFirst_name(), "INVITATION");

            return invitation;
        }
        return null;
    }

    public List<Invitation> getUserInvitations(Long userId) {
        UserDB user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return invitationRepository.findByRecipient(user);
        }
        return null;
    }

    public Invitation respondToInvitation(Long invitationId, String status) {
        return invitationRepository.findById(invitationId).map(invitation -> {
            invitation.setStatus(status);
            invitationRepository.save(invitation);

            notificationService.createNotification(invitation.getSender().getUser_id(), "Your invitation has been " + status.toLowerCase(), "INVITATION_RESPONSE");

            return invitation;
        }).orElse(null);
    }
}
