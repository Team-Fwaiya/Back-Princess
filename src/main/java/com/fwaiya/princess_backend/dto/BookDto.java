package com.fwaiya.princess_backend.dto;

import com.fwaiya.princess_backend.domain.Book;
import lombok.*;

// BookDto는 클라이언트와 Book 데이터를 주고받기 위한 전용 객체
// 외부 노출 시에는 DTO를 통해 안전하고 유연하게 데이터를 주고받음

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 전체 필드를 인자로 받는 생성자 자동 생성
@Builder // 빌더 패턴 사용을 위한 어노테이션
public class BookDto {

    private Long id; // Book 엔티티의 기본키 (book_id)
    private String title; // 책 제목
    private String author; // 저자
    private String genre; // 장르 (소설, 에세이, 자기계발 등)
    private String coverImageUrl; // 책 표지 이미지 URL (S3 경로)
    private String hashtags; // 해시태그 문자열 (예: "#고전#추천")

    // Entity → DTO 변환 메서드
    // 주로 Controller나 Service에서 DB 조회 결과를 클라이언트에게 반환할 때 사용
    public static BookDto fromEntity(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .coverImageUrl(book.getCoverImageUrl())
                .hashtags(book.getHashtags())
                .build();
    }

    //  DTO → Entity 변환 메서드
    //  주로 클라이언트에서 전달받은 데이터를 DB에 저장할 때 사용
    public Book toEntity() {
        return Book.builder()
                .title(this.title)
                .author(this.author)
                .genre(this.genre)
                .coverImageUrl(this.coverImageUrl)
                .hashtags(this.hashtags)
                .build();
    }
}
