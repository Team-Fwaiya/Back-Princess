package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
