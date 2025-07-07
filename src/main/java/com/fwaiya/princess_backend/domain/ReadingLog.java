package com.fwaiya.princess_backend.domain;

import com.fwaiya.princess_backend.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** 독서록 엔티티 **/
@Entity
@Setter
@Getter
public class ReadingLog extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String OneLineReview;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Min(0)
    @Max(5)
    private int rating; // 별 0~5개

    // 독서록과 책 연관관계 (1:1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    // 독서록과 유저 연관관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 독서록 생성될 때 user의 독서록에 추가
    public ReadingLog(User user, String OneLineReview, String content, int rating) {
        this.user = user;
        this.OneLineReview = OneLineReview;
        this.content = content;
        this.rating = rating;

        user.getReadingLogs().add(this);
    }

}

