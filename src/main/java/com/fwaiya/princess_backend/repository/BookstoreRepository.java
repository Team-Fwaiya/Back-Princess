package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.Bookstore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {

    // 시도와 시군구로 서점 검색 (최대 5개)
    List<Bookstore> findByCtprvnNmContainingAndSignguNmContaining(String ctprvnNm, String signguNm, Pageable pageable);

    // 시도만으로 검색 (백업용)
    List<Bookstore> findByCtprvnNmContaining(String ctprvnNm, Pageable pageable);
}