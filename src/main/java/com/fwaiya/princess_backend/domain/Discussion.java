package com.fwaiya.princess_backend.domain;


import com.fwaiya.princess_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**독서 토론 방 엔티티 클래스**/
@Entity
@Setter
@Getter
public class Discussion extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //토론방 id

    @Column(nullable = false)
    private String title;

    @Lob // 최대 65,535자
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status; // 삭제 시에 상태만 변경

    // 독서토론과 책 연관관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    // 독서토론과 댓글 연관관계 (1:N)
    // 독서토론 생성 될 때 책에도 추가-> 필요 없나
}
