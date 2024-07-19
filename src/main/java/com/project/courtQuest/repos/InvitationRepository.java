package com.project.courtQuest.repos;

import com.project.courtQuest.entities.Invitation;
import com.project.courtQuest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByRecipient(User recipient);
    List<Invitation> findBySender(User sender);
}
