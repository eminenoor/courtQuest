package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.Invitation;
import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("SELECT i FROM Invitation i JOIN FETCH i.sender JOIN FETCH i.activity WHERE i.recipient = :recipient")
    List<Invitation> findByRecipient(@Param("recipient") UserDB recipient);
    List<Invitation> findBySender(UserDB sender);

    @Modifying
    @Transactional
    @Query("DELETE FROM Invitation i WHERE i.invitationId = :id")
    void deleteById(@Param("id") Long id);
}
