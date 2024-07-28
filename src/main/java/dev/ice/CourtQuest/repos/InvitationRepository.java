package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.Invitation;
import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByRecipient(UserDB recipient);
    List<Invitation> findBySender(UserDB sender);
}
