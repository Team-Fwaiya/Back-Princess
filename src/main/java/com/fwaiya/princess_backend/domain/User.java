package com.fwaiya.princess_backend.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/** * 사용자 엔티티 클래스 ** @author yaaan7 @since 2025-06-26 */
@Entity
@Setter
@Getter
public class User {

    @Id
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // = userId
    @Column(unique = true, nullable = false)
    private String username;

    // 8글자 제한
    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    // 기본 값 설정하기
    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String role;
}
