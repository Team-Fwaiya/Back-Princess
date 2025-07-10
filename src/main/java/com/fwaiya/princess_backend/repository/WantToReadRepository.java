package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.WantToRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WantToReadRepository extends JpaRepository<WantToRead, Long> {

    Optional<WantToRead> findByIdAndUserId(Long id, Long userId);
}
