package com.fwaiya.princess_backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false) // 수정시 관여 X
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false) // 삽입시 관여 X
    private LocalDateTime updatedAt;
}

