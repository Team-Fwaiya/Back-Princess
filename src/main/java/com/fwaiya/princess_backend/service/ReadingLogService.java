package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.domain.ReadingLog;
import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.request.BookRequest;
import com.fwaiya.princess_backend.dto.request.ReadingLogRequest;
import com.fwaiya.princess_backend.dto.response.ReadingLogResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.repository.BookRepository;
import com.fwaiya.princess_backend.repository.ReadingLogRepository;
import com.fwaiya.princess_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ReadingLogService
 * 독서록의 등록, 조회, 수정, 삭제에 대한 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class ReadingLogService {

    private final ReadingLogRepository readingLogRepository;
    private final UserRepository userRepository;
    private final BookService bookService; // Book 등록 또는 재사용을 위임받음
    private final S3Service s3Service;

    /**
     * 독서록 등록
     * - 책 정보가 함께 전달됨
     * - 책이 DB에 없으면 자동 등록
     * - 책이 이미 있으면 해당 Book에 연결
     */

    @Transactional
    public void createWithBookUpsert(
            BookRequest bookReq,
            MultipartFile coverImage,
            String content,
            Integer rating,
            CustomUserDetails cu
    ) {
        // 1) 사용자
        User user = userRepository.findByUsername(cu.getUsername())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        // 2) 책 업서트 (createReadingLog와 동일한 정책으로 통일)
        //    - title + author + genre 기준으로 조회, 없으면 생성
        Book book = bookService.findOrCreateBook(bookReq);

        // 해시태그가 비어 있거나 기존 책에 없으면 최신 값으로 보정
        if ((book.getHashtags() == null || book.getHashtags().isBlank())
                && bookReq.getHashtags() != null) {
            book.setHashtags(bookReq.getHashtags());
        }

        // 3) 커버 파일이 있으면 S3 업로드 후 URL 교체 (파일 우선)
        if (coverImage != null && !coverImage.isEmpty()) {
            String url = s3Service.uploadBookCoverImage(coverImage);
            book.setCoverImageUrl(url);
            // JPA 변경감지로 커버 URL이 갱신됨
        } else if (book.getCoverImageUrl() == null && bookReq.getCoverImageUrl() != null) {
            // (연착륙) 파일이 없고 DB에도 없을 때만, 요청의 URL을 한시 허용
            book.setCoverImageUrl(bookReq.getCoverImageUrl());
        }

        // 4) 독서록 생성
        ReadingLog log = new ReadingLog();
        log.setUser(user);
        log.setBook(book);
        log.setContent(content);
        log.setRating(rating);
        readingLogRepository.save(log);

        // 5) 부가 처리
        user.updateReadCountAndReadingLevel();
    }


//    @Transactional
//    public void createReadingLog(ReadingLogRequest request, CustomUserDetails customUserDetails) {
//        // 사용자 조회 (JWT 기반 인증 사용자)
//        User user = userRepository.findByUsername(customUserDetails.getUsername())
//                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
//
//        // 책 중복 검사 후 없으면 새로 등록
//        Book book = bookService.findOrCreateBook(request.getBook());
//
//        // 독서록 생성 및 필드 설정
//        ReadingLog readingLog = new ReadingLog();
//        readingLog.setUser(user);
//        readingLog.setBook(book);
//        /*readingLog.setOneLineReview(request.getOneLineReview());*/
//        readingLog.setContent(request.getContent());
//        readingLog.setRating(request.getRating());
//
//        // 저장
//        readingLogRepository.save(readingLog);
//
//        // 사용자의 읽은 책 수 및 레벨 갱신
//        user.updateReadCountAndReadingLevel();
//    }

    /**
     * 내 독서록 전체 목록 조회
     * - 현재 로그인한 사용자가 작성한 모든 독서록을 반환
     */
    @Transactional
    public List<ReadingLogResponse> getMyReadingLogs(CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        List<ReadingLog> logs = readingLogRepository.findByUserId(user.getId());

        // ReadingLog → ReadingLogResponse 변환
        return logs.stream()
                .map(ReadingLogResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 내 독서록 단건 조회
     * - ID로 조회하되, 본인의 독서록만 접근 가능
     */
    @Transactional
    public ReadingLogResponse getReadingLogById(Long readingLogId, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        ReadingLog log = readingLogRepository.findById(readingLogId)
                .orElseThrow(() -> new GeneralException(ErrorCode.READING_LOG_NOT_FOUND));

        // 소유자 확인
        if (!log.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorCode.UNAUTHORIZED_READING_LOG_ACCESS);
        }

        return ReadingLogResponse.from(log);
    }

    /**
     * 독서록 수정
     * - 한 줄 평, 감상 내용, 평점만 수정 가능
     * - 책 정보는 수정하지 않음
     */
    @Transactional
    public void updateReadingLog(Long readingLogId, ReadingLogRequest request, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        ReadingLog log = readingLogRepository.findById(readingLogId)
                .orElseThrow(() -> new GeneralException(ErrorCode.READING_LOG_NOT_FOUND));

        // 소유자 확인
        if (!log.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorCode.UNAUTHORIZED_READING_LOG_UPDATE);
        }

        // 필드 수정
        // log.setOneLineReview(request.getOneLineReview());
        log.setContent(request.getContent());
        log.setRating(request.getRating());
    }

    /**
     * 독서록 삭제
     * - 본인만 삭제 가능
     */
    @Transactional
    public void deleteReadingLog(Long readingLogId, CustomUserDetails customUserDetails) {
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        ReadingLog log = readingLogRepository.findById(readingLogId)
                .orElseThrow(() -> new GeneralException(ErrorCode.READING_LOG_NOT_FOUND));

        // 소유자 확인
        if (!log.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorCode.UNAUTHORIZED_READING_LOG_DELETE);
        }

        readingLogRepository.delete(log);
    }
}