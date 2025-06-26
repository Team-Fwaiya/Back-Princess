package com.fwaiya.princess_backend.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class User {

    @Id
    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 8글자 제한
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 프로필 사진 S3 url
    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String role;
}
