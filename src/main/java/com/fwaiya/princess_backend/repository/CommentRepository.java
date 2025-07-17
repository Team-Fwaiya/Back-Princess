package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
