package com.fwaiya.princess_backend.domain;


import com.fwaiya.princess_backend.global.BaseEntity;
import com.fwaiya.princess_backend.global.constant.ReadingLevel;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** * 사용자 엔티티 클래스 ** @author yaaan7 @since 2025-06-26 */
@Entity
@Setter
@Getter
public class User extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // = userId
    @Column(unique = true, nullable = false)
    private String username;

    // 8글자 제한, 닉네임
    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    // 기본 값 수정하기
    @Column(nullable = false)
    private String imagePath= "default.jpg";;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel = ReadingLevel.lower;

    @Column(nullable = false)
    private int read_count = 0;

    // 유저와 댓글 연관관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // 부모가 삭제되면 자식 엔티티도 삭제함 -> 부모엔티티에서 관리
    // 부모 엔티티 측에서 자식 엔티티를 제거하면 자식 엔티티 자체도 제거됨
    // (null 처리에 관해서 따로 로직이 없으므로)
    private List<Comment> comments = new ArrayList<>();

    // 유저와 독서록 연관관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingLog> readingLogs = new ArrayList<>();

    // 유저와 읽고 싶은 책 연관관계 (1:N)
}
