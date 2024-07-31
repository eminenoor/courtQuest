package dev.ice.CourtQuest.services;

import dev.ice.CourtQuest.entities.Activity;
import dev.ice.CourtQuest.entities.Invitation;
import dev.ice.CourtQuest.entities.UserDB;
import dev.ice.CourtQuest.repos.ActivityRepository;
import dev.ice.CourtQuest.repos.InvitationRepository;
import dev.ice.CourtQuest.repos.NotificationRepository;
import dev.ice.CourtQuest.repos.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ActivityService activityService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
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

            String message = "You have a new invitation from " + sender.getFirst_name();
            notificationService.createNotification(recipientId, message, "INVITATION",
                    activity.getName(), activity.getDate(), activity.getTime(), activity.getPlace());

            return invitation;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Invitation> getUserInvitations(Long userId) {
        UserDB user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return invitationRepository.findByRecipient(user);
        }
        return null;
    }

    @Transactional
    public Invitation respondToInvitation(Long invitationId, String status) {
        return invitationRepository.findById(invitationId).map(invitation -> {
            invitation.setStatus(status);
            invitationRepository.save(invitation);

            String message = "Your invitation has been " + status.toLowerCase();
            notificationService.createNotification(invitation.getSender().getUser_id(), message, "INVITATION_RESPONSE",
                    invitation.getActivity().getName(), invitation.getActivity().getDate(), invitation.getActivity().getTime(), invitation.getActivity().getPlace());

            return invitation;
        }).orElse(null);
    }

    @Transactional
    public void respondToInvitationVoid(Long invitationId, String response) {
        try {
            Invitation invitation = invitationRepository.findById(invitationId).orElse(null);
            if (invitation != null) {
                UserDB user = invitation.getRecipient();
                Activity activity = invitation.getActivity();

                if ("Accepted".equals(response)) {
                    activityService.addParticipant(activity.getActivityId(), user.getUser_id());
                }
                invitationRepository.deleteById(invitationId);
                String message = "Your invitation has been " + response.toLowerCase();
                notificationService.createNotification(invitation.getSender().getUser_id(), message, "INVITATION_RESPONSE",
                        activity.getName(), activity.getDate(), activity.getTime(), activity.getPlace());
            } else {
                System.out.println("Invitation with ID " + invitationId + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Exception occurred while deleting invitation with ID " + invitationId);
            e.printStackTrace();
        }
    }





}