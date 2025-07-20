package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.global.constant.Genre;
import lombok.Builder;
import lombok.Getter;

/**
 * [BookResponse]
 * 책 정보를 클라이언트에게 응답할 때 사용하는 DTO
 */
@Getter
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private Genre genre;
    private String coverImageUrl;
    private String hashtags;

    /**
     * Book 엔티티 → BookResponse 변환 메서드
     */
    public static BookResponse from(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .coverImageUrl(book.getCoverImageUrl())
                .hashtags(book.getHashtags())
                .build();
    }
}