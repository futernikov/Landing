package com.example.Landing.repo;

import com.example.Landing.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsername(String login);
}
