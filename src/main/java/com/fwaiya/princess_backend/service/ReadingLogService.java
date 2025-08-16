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

        // 3) 파일 없으면 바로 예외
        if (coverImage == null || coverImage.isEmpty()) {
            // 파일 없으면 바로 예외
            throw GeneralException.of(ErrorCode.INVALID_PROFILE_IMAGE);
        }

        // 파일 업로드(무조건)
        String url = s3Service.uploadBookCoverImage(coverImage);
        book.setCoverImageUrl(url);

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

    /** 수정: 등록과 동일하게 멀티파트 처리, 파일이 오면 교체 */
    @Transactional
    public void updateReadingLogWithMultipart(
            Long readingLogId,
            ReadingLogRequest req,
            MultipartFile coverImage,               // 파일 선택(있으면 교체)
            CustomUserDetails cu
    ) {
        User user = userRepository.findByUsername(cu.getUsername())
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        ReadingLog log = readingLogRepository.findById(readingLogId)
                .orElseThrow(() -> new GeneralException(ErrorCode.READING_LOG_NOT_FOUND));

        if (!log.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorCode.UNAUTHORIZED_READING_LOG_UPDATE);
        }

        // 내용/평점 갱신
        log.setContent(req.getContent());
        log.setRating(req.getRating());

        // 책 메타(제목/저자/장르) 변경 의도가 있다면 업서트(없다면 기존 그대로)
        BookRequest bookReq = req.getBook();
        if (bookReq != null) {
            Book newBook = bookService.findOrCreateBook(bookReq);
            log.setBook(newBook);

            // 해시태그 최신값 반영(선택)
            if ((newBook.getHashtags() == null || newBook.getHashtags().isBlank())
                    && bookReq.getHashtags() != null) {
                newBook.setHashtags(bookReq.getHashtags());
            }

            // 파일이 오면 표지 교체
            if (coverImage != null && !coverImage.isEmpty()) {
                String url = s3Service.uploadBookCoverImage(coverImage);
                newBook.setCoverImageUrl(url);
            }
        } else {
            // 책 변경 없이 커버만 교체하고 싶을 수 있음
            if (coverImage != null && !coverImage.isEmpty()) {
                String url = s3Service.uploadBookCoverImage(coverImage);
                log.getBook().setCoverImageUrl(url);
            }
        }
    }

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