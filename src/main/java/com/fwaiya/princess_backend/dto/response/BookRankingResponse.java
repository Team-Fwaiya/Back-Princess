package com.fwaiya.princess_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 독서록 수 기준 책 랭킹 응답 DTO
 * - 홈 화면에서 랭킹 리스트를 표시할 때 사용
 */
@Getter
@AllArgsConstructor
public class BookRankingResponse {

    private Long id;                 // 책 ID
    private String title;            // 책 제목
    private String author;           // 저자
    private String hashtags;         // 책 해시태그
    private String coverImageUrl;    // 표지 이미지 URL
    private long readingLogCount;    // 해당 책의 독서록 작성 수
}

