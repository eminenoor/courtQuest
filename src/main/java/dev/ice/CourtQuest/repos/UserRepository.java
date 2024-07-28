package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDB, Long> {
    UserDB findByEmail(String email);
}
