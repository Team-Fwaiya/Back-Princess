package com.fwaiya.princess_backend.domain;

import com.fwaiya.princess_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libraries")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Library extends BaseEntity {

    @Column(name = "library_name", nullable = false)
    private String libraryName; // 도서관명

    @Column(name = "sido", nullable = false)
    private String sido; // 시도명

    @Column(name = "sigungu", nullable = false)
    private String sigungu; // 시군구명

    @Column(name = "address")
    private String address; // 주소
}