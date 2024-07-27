package dev.ice.CourtQuest.repos;

import dev.ice.CourtQuest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
