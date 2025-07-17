package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.ReadingLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ReadingLogResponse
 * 독서록 조회 시 사용하는 응답 DTO 클래스입니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingLogResponse {

    private String oneLineReview;
    private String content;
    private Integer rating;

    private String bookTitle;
    private String bookAuthor;
    private String bookGenre;
    private String bookHashtags;
    private String bookCoverImageUrl;

    private LocalDateTime createdAt;

    /**
     * ReadingLog 엔티티로부터 ReadingLogResponse 생성
     */
    public static ReadingLogResponse from(ReadingLog readingLog) {
        return new ReadingLogResponse(
                readingLog.getOneLineReview(),
                readingLog.getContent(),
                readingLog.getRating(),
                readingLog.getBook().getTitle(),
                readingLog.getBook().getAuthor(),
                readingLog.getBook().getGenre(),
                readingLog.getBook().getHashtags(),
                readingLog.getBook().getCoverImageUrl(),
                readingLog.getCreatedAt()
        );
    }
}
