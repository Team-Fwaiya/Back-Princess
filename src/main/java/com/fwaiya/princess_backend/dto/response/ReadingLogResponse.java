package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.ReadingLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * [ReadingLogResponse]
 * 독서록 단건 또는 목록 조회 시 클라이언트에게 전달되는 응답 DTO
 * 독서록 자체 정보와 함께 연결된 책(Book)의 정보도 포함
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

    // 연결된 책의 장르 (Enum → 문자열로 변환됨)
    private String bookGenre;

    private String bookHashtags;

    // 책 표지 이미지 URL (S3 기반)
    private String bookCoverImageUrl;

    private LocalDateTime createdAt;

    /**
     * ReadingLog 엔티티로부터 응답 DTO 객체 생성
     * - Book 엔티티 내부의 필드들을 함께 추출하여 응답에 포함시킴
     */
    public static ReadingLogResponse from(ReadingLog readingLog) {
        return new ReadingLogResponse(
                readingLog.getOneLineReview(),
                readingLog.getContent(),
                readingLog.getRating(),
                readingLog.getBook().getTitle(),
                readingLog.getBook().getAuthor(),
                readingLog.getBook().getGenre().name(),  // Enum → String
                readingLog.getBook().getHashtags(),
                readingLog.getBook().getCoverImageUrl(),
                readingLog.getCreatedAt()
        );
    }
}
