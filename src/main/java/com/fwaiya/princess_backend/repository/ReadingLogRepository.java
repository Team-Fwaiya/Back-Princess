package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.ReadingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * [ReadingLogRepository]
 * 독서록(ReadingLog) 엔티티에 대한 DB 접근을 담당하는 JPA Repository
 * 기본 CRUD 외에도 사용자 정의 조회 메서드 추가
 */
@Repository
public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {

    /**
     * 특정 사용자 ID 기준으로 작성한 독서록 전체 목록 조회
     * - 내 독서록 리스트 조회 용도
     */
    List<ReadingLog> findByUserId(Long userId);
}