package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.dto.request.BookRequest;
import com.fwaiya.princess_backend.dto.response.BookResponse;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController
 * 책 등록, 조회, 수정, 삭제 API를 제공하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "04. 책 관리", description = "책 정보 등록, 조회, 수정, 삭제 API")
public class BookController {

    private final BookService bookService;

    /**
     * 책 등록
     */
    @PostMapping
    @Operation(summary = "책 등록", description = "새로운 책 정보를 등록합니다.")
    public ApiResponse<String> createBook(@RequestBody BookRequest request) {
        bookService.saveBook(request);
        return ApiResponse.onSuccess(SuccessCode.BOOK_CREATE_SUCCESS, "True");
    }

    /**
     * 전체 책 목록 조회
     */
    @GetMapping
    @Operation(summary = "전체 책 목록 조회", description = "등록된 모든 책 정보를 조회합니다.")
    public ApiResponse<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ApiResponse.onSuccess(SuccessCode.BOOK_LIST_GET_SUCCESS, books);
    }

    /**
     * 특정 책 상세 조회
     */
    @GetMapping("/{bookId}")
    @Operation(summary = "책 상세 조회", description = "책 ID를 통해 특정 책 정보를 조회합니다.")
    public ApiResponse<BookResponse> getBookById(@PathVariable Long bookId) {
        BookResponse book = bookService.getBook(bookId);
        return ApiResponse.onSuccess(SuccessCode.BOOK_DETAIL_GET_SUCCESS, book);
    }

    /**
     * 책 정보 수정
     */
    @PutMapping("/{bookId}")
    @Operation(summary = "책 정보 수정", description = "책 ID를 기준으로 책 정보를 수정합니다.")
    public ApiResponse<String> updateBook(@PathVariable Long bookId, @RequestBody BookRequest request) {
        bookService.updateBook(bookId, request);
        return ApiResponse.onSuccess(SuccessCode.BOOK_UPDATE_SUCCESS, "True");
    }

    /**
     * 책 삭제
     */
    @DeleteMapping("/{bookId}")
    @Operation(summary = "책 삭제", description = "책 ID를 기준으로 해당 책을 삭제합니다.")
    public ApiResponse<String> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ApiResponse.onSuccess(SuccessCode.BOOK_DELETE_SUCCESS, "True");
    }
}
