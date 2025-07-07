package com.fwaiya.princess_backend.domain;

import com.fwaiya.princess_backend.global.BaseEntity;
import com.fwaiya.princess_backend.global.constant.Genre;
import com.fwaiya.princess_backend.global.constant.ReadingLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Book extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    //
    @Enumerated(EnumType.STRING)
    private Genre genre;

    // 이미지 업로드 안할 경우를 위해 nullable 하게
    private String cover_image_url;

    @Column(nullable = false)
    private String hashtags;

    // 책과 독서토론 연관관계 (1:N)
    // 책과 독서록 연관관계 (1:N)
}
