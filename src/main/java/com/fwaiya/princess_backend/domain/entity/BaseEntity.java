package com.fwaiya.princess_backend.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 이 클래스를 상속받는 클래스들이 공통으로 필드를 가지게 함
@EntityListeners(AuditingEntityListener.class) // 생성/수정 시간을 자동으로 채워줌
@Getter
public abstract class BaseEntity {

    @CreationTimestamp // 최초 생성 시간 자동 기록
    @Column(updatable = false) // 업데이트 시에는 변경되지 않도록 설정
    private LocalDateTime createdAt;

    @UpdateTimestamp // 매번 업데이트 시 자동 갱신
    @Column(insertable = false) // 생성 시에는 무시되도록 설정
    private LocalDateTime updatedAt;
}
