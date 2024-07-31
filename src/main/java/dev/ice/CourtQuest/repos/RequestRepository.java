package dev.ice.CourtQuest.repos;


import dev.ice.CourtQuest.entities.Request;
import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRecipient(UserDB recipient);
    List<Request> findBySender(UserDB sender);

    @Modifying
    @Transactional
    @Query("DELETE FROM Request i WHERE i.requestId = :id")
    void deleteById(@Param("id") Long id);
}
