package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.domain.ReadingLog;
import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.request.ReadingLogRequest;
import com.fwaiya.princess_backend.dto.response.ReadingLogResponse;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.repository.BookRepository;
import com.fwaiya.princess_backend.repository.ReadingLogRepository;
import com.fwaiya.princess_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingLogService {
    private final ReadingLogRepository readingLogRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /** 독서록 등록 기능 **/
    @Transactional
    public void createReadingLog(ReadingLogRequest request, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Book book=bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));


        ReadingLog readingLog = new ReadingLog();
        readingLog.setUser(user);
        readingLog.setBook(book);
        readingLog.setOneLineReview(request.getOneLineReview());
        readingLog.setContent(request.getContent());
        readingLog.setRating(request.getRating());

        readingLogRepository.save(readingLog);
    }

    /** 내 독서록 목록 조회 기능 **/
    @Transactional
    public List<ReadingLogResponse> getMyReadingLogs(CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        List<ReadingLog> logs = readingLogRepository.findByUserId(user.getId());
        return logs.stream().map(ReadingLogResponse::from).collect(Collectors.toList());
    }

    /** 내 독서록 단건 조회 기능 **/
    @Transactional
    public ReadingLogResponse getReadingLogById(Long readingLogId, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ReadingLog log = readingLogRepository.findById(readingLogId).orElseThrow();

        if (!log.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 독서록만 조회 가능합니다.");
        }

        return ReadingLogResponse.from(log);
    }

    /** 독서록 수정 기능 **/
    @Transactional
    public void updateReadingLog(Long readingLogId, ReadingLogRequest request, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ReadingLog log = readingLogRepository.findById(readingLogId).orElseThrow();

        if (!log.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 독서록만 수정 가능합니다.");
        }

        log.setOneLineReview(request.getOneLineReview());
        log.setContent(request.getContent());
        log.setRating(request.getRating());
    }

    /** 독서록 삭제 기능 **/
    @Transactional
    public void deleteReadingLog(Long readingLogId, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ReadingLog log = readingLogRepository.findById(readingLogId).orElseThrow();

        if (!log.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 독서록만 삭제 가능합니다.");
        }

        readingLogRepository.delete(log);
    }
}