package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.dto.request.BookRequest;
import com.fwaiya.princess_backend.dto.response.BookRankingResponse;
import com.fwaiya.princess_backend.dto.response.BookResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest; // ✅ 올바른 import
import org.springframework.data.domain.Pageable;   // ✅ 올바른 import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BookService
 * 책 등록, 조회,
 * 수정, 삭제와 같은 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 책 등록
     */
    @Transactional
    public void saveBook(BookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .coverImageUrl(request.getCoverImageUrl())
                .hashtags(request.getHashtags())
                .build();

        bookRepository.save(book);
    }

    /**
     * 전체 책 목록 조회
     */
    @Transactional
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponse::from) // Book → BookResponse
                .collect(Collectors.toList());
    }

    /**
     * 단일 책 상세 조회
     */
    @Transactional
    public BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorCode.BOOK_NOT_FOUND));

        return BookResponse.from(book);
    }

    /**
     * 책 정보 수정
     */
    @Transactional
    public void updateBook(Long id, BookRequest request) {
        Book original = bookRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorCode.BOOK_NOT_FOUND));

        Book updated = Book.builder()
                .id(original.getId()) // ID 유지
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .coverImageUrl(request.getCoverImageUrl())
                .hashtags(request.getHashtags())
                .build();

        bookRepository.save(updated); // 덮어쓰기
    }

    /**
     * 책 삭제
     */
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new GeneralException(ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    /**
     * [findOrCreateBook]
     * title + author + genre 기준으로 책이 존재하면 반환,
     * 존재하지 않으면 새로 등록한 뒤 반환
     * → 독서록 작성 시 책 정보 기반으로 연결할 때 사용
     */
    @Transactional
    public Book findOrCreateBook(BookRequest request) {
        return bookRepository.findByTitleAndAuthorAndGenre(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre()
        ).orElseGet(() -> {
            Book newBook = Book.builder()
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .genre(request.getGenre())
                    .coverImageUrl(request.getCoverImageUrl())
                    .hashtags(request.getHashtags())
                    .build();
            return bookRepository.save(newBook);
        });
    }

    /**
     * 독서록이 가장 많이 작성된 책 TOP 8을 조회
     * - PageRequest.of(0, 8)로 최대 8개 제한
     */

    @Transactional
    public List<BookRankingResponse> getBookRanking() {
        Pageable pageable = PageRequest.of(0, 8); // 최대 8개
        return bookRepository.findTop8BooksByReadingLogCount(pageable);
    }

}