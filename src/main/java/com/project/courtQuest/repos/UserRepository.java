package com.project.courtQuest.repos;

import com.project.courtQuest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
