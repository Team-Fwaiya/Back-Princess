package com.fwaiya.princess_backend.repository;

import com.fwaiya.princess_backend.domain.ReadingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReadingLogRepository
 * 독서록 데이터를 관리하는 JPA Repository 인터페이스
 */
@Repository
public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {

    /**
     * 특정 사용자 ID로 독서록 목록 조회
     * @param userId 사용자 ID
     * @return 독서록 리스트
     */
    List<ReadingLog> findByUserId(Long userId);

}
