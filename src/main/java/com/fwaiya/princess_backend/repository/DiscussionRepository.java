package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Discussion;
import com.fwaiya.princess_backend.global.constant.DiscussionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findAllByStatus(DiscussionStatus status);
    Optional<Discussion> findByIdAndStatus(Long id, DiscussionStatus status);

    List<Discussion> findTop8ByOrderByCreatedAtDesc();
    List<Discussion> findByStatusAndIdNotIn(DiscussionStatus status, List<Long> top8Ids);
    List<Discussion> findByStatus(DiscussionStatus status);
}
