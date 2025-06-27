package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUserId(String userId);
    Boolean existsByNickname(String nickname);
}
