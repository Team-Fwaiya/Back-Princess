package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.domain.Discussion;
import com.fwaiya.princess_backend.dto.request.DiscussionCreateRequest;
import com.fwaiya.princess_backend.dto.response.DiscussionResponse;
import com.fwaiya.princess_backend.global.BaseEntity;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.constant.DiscussionStatus;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.repository.BookRepository;
import com.fwaiya.princess_backend.repository.DiscussionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final BookRepository bookRepository;

    public Discussion getActiveDiscussion(Long discussionId){
        return discussionRepository.findByIdAndStatus(discussionId, DiscussionStatus.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorCode.DISCUSSION_NOT_FOUND));
    }

    /** 토론방 전체 조회 **/
    @Transactional
    public List<DiscussionResponse> getAllDiscussions() {
        return discussionRepository.findAllByStatus(DiscussionStatus.ACTIVE).stream()
                .map(DiscussionResponse::from)
                .collect(Collectors.toList());
    }


    /** 토론방 개별 조회 **/
    @Transactional
    public DiscussionResponse getDiscussionById(Long discussionId) {

        Discussion discussion = getActiveDiscussion(discussionId);

        return DiscussionResponse.from(discussion);
    }


    /** 토론방 등록 **/
    @Transactional
    public DiscussionResponse createDiscussion(DiscussionCreateRequest request) {
        // 책 제목 조회
        Book book = bookRepository.findByTitle(request.getBookTitle())
                //.orElseThrow(() -> new IllegalArgumentException("해당 제목의 책이 존재하지 않습니다: " + request.getBookTitle()));
                .orElseThrow(() -> new GeneralException(ErrorCode.BOOK_TITLE_NOT_FOUND));

        Discussion discussion = Discussion.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .book(book)
                .status(DiscussionStatus.ACTIVE)
                .endDate(LocalDateTime.now().plusWeeks(1))
                //.endDate(LocalDateTime.now().plusMinutes(1))
                .build();

        Discussion saved = discussionRepository.save(discussion);
        return DiscussionResponse.from(saved);
    }

    /** 토론방 삭제 **/
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateDiscussionStatus(){

        // 활성화 되어 있고 일주일 전에 생성된 주문 찾기
        // 정확히 일주일 전이 아닌 날짜만 보게끔 고쳐보자
        List<Discussion> expiredDiscussions = discussionRepository.findByStatusAndEndDateBefore(
                DiscussionStatus.ACTIVE,
                LocalDateTime.now()
        );

        // 상태 'CANCELED'로 변경
        for (Discussion discussion : expiredDiscussions) {
            discussion.updateStatus(DiscussionStatus.CANCELLED);
        }
    }
}
