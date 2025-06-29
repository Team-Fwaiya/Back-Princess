package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);
    Boolean existsByNickname(String nickname);

    Optional<User> findByUsername(String username);
}
