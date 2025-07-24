package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.global.constant.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
