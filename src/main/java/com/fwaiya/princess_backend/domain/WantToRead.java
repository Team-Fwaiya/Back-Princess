package com.fwaiya.princess_backend.domain;

import com.fwaiya.princess_backend.global.BaseEntity;
import com.fwaiya.princess_backend.global.constant.Genre;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/** 읽고 싶은 책 엔티티 **/
@Entity
@Setter
@Getter
public class WantToRead extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    // 읽고 싶은 책과 유저 연관관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
