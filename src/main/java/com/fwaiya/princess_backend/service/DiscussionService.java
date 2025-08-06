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

        updateDiscussionStatus();
        return DiscussionResponse.from(saved);
    }

    /** 토론방 최신 8개만 ACTIVE 상태로 **/
    // 홈화면에 8개만 뜨고 + 나머지는 비활성화로 변경
    //@Scheduled(fixedRate = 3600000)
    @Transactional
    public void updateDiscussionStatus(){

        // 최신 토론방 8개
        List<Discussion> top8 = discussionRepository.findTop8ByOrderByCreatedAtDesc();

        // 8개 없는데 활성화인 토론방 찾기
        List<Discussion> cancelled = discussionRepository.findByStatusAndIdNotIn(
                DiscussionStatus.ACTIVE,
                top8.stream().map(Discussion::getId).toList()
        );

        for (Discussion discussion : cancelled) {
            discussion.updateStatus(DiscussionStatus.CANCELLED);
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void deleteCancelledDiscussion(){

        List<Discussion> cancelled = discussionRepository.findByStatus(
                DiscussionStatus.CANCELLED
        );

        discussionRepository.deleteAll(cancelled);
    }
}
