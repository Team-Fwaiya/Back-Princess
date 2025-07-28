package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Library;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    // 시도와 시군구로 도서관 검색 (최대 5개)
    List<Library> findBySidoContainingAndSigunguContaining(String sido, String sigungu, Pageable pageable);

    // 시도만으로 검색 (백업용)
    List<Library> findBySidoContaining(String sido, Pageable pageable);
}