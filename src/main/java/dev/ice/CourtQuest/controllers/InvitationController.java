package dev.ice.CourtQuest.controllers;

import dev.ice.CourtQuest.entities.Invitation;
import dev.ice.CourtQuest.services.InvitationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping
    public Invitation sendInvitation(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestParam Long activityId) {
        return invitationService.sendInvitation(senderId, recipientId, activityId);
    }

    @GetMapping("/user/{userId}")
    public List<Invitation> getUserInvitations(@PathVariable Long userId) {
        return invitationService.getUserInvitations(userId);
    }

    @PutMapping("/{invitationId}/respond")
    public Invitation respondToInvitation(@PathVariable Long invitationId, @RequestParam String status) {
        return invitationService.respondToInvitation(invitationId, status);
    }
}

