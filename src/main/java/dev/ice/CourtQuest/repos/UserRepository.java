package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserDB, Long> {
    UserDB findByEmail(String email);

    @Query("SELECT u FROM UserDB u LEFT JOIN FETCH u.activities WHERE u.email = :email")
    UserDB findByEmailWithActivities(String email);
}
