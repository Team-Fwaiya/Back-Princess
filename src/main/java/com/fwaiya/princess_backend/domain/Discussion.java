package com.fwaiya.princess_backend.domain;


import com.fwaiya.princess_backend.dto.response.DiscussionResponse;
import com.fwaiya.princess_backend.global.BaseEntity;
import com.fwaiya.princess_backend.global.constant.DiscussionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**독서 토론 방 엔티티 클래스**/
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discussion extends BaseEntity {

    @Id
    @Column(name = "discussion_id", unique = true, nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //토론방 id

    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
//    private LocalDate startDate;
//
    @Column(nullable = false)
    //private LocalDate endDate;
    private LocalDateTime endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscussionStatus status = DiscussionStatus.ACTIVE; // 삭제 시에 상태만 변경

    // 독서토론과 책 연관관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    // 독서토론과 댓글 연관관계 (1:N)
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 상태 update
    public void updateStatus(DiscussionStatus status){
        this.status = status;
    }

    // 댓글 추가 메서드
    public void addComment(Comment comment){
        comments.add(comment);
        comment.setDiscussion(this);
    }
}
