package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Book 엔티티에 대한 DB 접근을 담당하는 JPA Repository 인터페이스
// JpaRepository<Book, Long>을 상속받아 기본 CRUD 기능을 자동으로 제공
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 기본적으로 다음과 같은 메서드를 자동으로 제공함:
    // - findAll() : 전체 목록 조회
    // - findById(Long id) : ID로 단건 조회
    // - save(Book book) : 삽입 또는 수정
    // - deleteById(Long id) : 삭제
    // - existsById(Long id) : 존재 여부 확인

}
