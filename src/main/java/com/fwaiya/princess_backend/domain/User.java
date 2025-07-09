package com.fwaiya.princess_backend.domain;


import com.fwaiya.princess_backend.global.BaseEntity;
import com.fwaiya.princess_backend.global.constant.ReadingLevel;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** * 사용자 엔티티 클래스 ** @author yaaan7 @since 2025-06-26 */
// id, username, nickname, password, imagePath, birthDate, readingLevel
@Entity
@Setter
@Getter
@NoArgsConstructor
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
    private String imagePath= "https://s3.bucket.com/profile/default2.png";;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel = ReadingLevel.lower;

    @Column(nullable = false)
    private int readCount = 0;

    // 유저와 댓글 연관관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 유저와 독서록 연관관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingLog> readingLogs = new ArrayList<>();

    // 유저와 읽고 싶은 책 연관관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WantToRead> wantToReads = new ArrayList<>();

    // WantToRead와 양방향 편의 메서드
    public void addWantToRead(WantToRead wantToRead) {
        this.wantToReads.add(wantToRead);
        wantToRead.setUser(this);
    }

    // 다음 레벨까지 몇 권 남았는지 계산
    public int CountUntilNextLevel(){
        return 5 - ( readCount % 5 );
    }

    // 프로필 사진 업데이트
    public void updateImagePath(String newPath){
        this.imagePath = newPath;
    }
}
