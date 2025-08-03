package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.dto.response.BookRankingResponse;
import com.fwaiya.princess_backend.global.constant.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * BookRepository
 * Book 엔티티에 대한 JPA Repository
 * 기본 CRUD + 사용자 정의 쿼리 메서드
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 제목으로 책 조회
    Optional<Book> findByTitle(String title);

    // 제목 + 저자 + 장르가 모두 일치하는 책 조회 (중복 방지용)
    Optional<Book> findByTitleAndAuthorAndGenre(String title, String author, Genre genre);

    /**
     * 독서록이 가장 많이 작성된 책 상위 8개 조회
     * - ReadingLog를 기준으로 book_id 별 COUNT
     * - 개수가 8개 미만이면 있는 만큼만 반환
     */
    @Query("""
    SELECT new com.fwaiya.princess_backend.dto.response.BookRankingResponse(
        b.id,
        b.title,
        b.author,
        b.hashtags,
        b.coverImageUrl,
        COUNT(rl.id)
    )
    FROM ReadingLog rl
    JOIN rl.book b
    GROUP BY b.id
    ORDER BY COUNT(rl.id) DESC
""")
    List<BookRankingResponse> findTop8BooksByReadingLogCount(Pageable pageable);


}
