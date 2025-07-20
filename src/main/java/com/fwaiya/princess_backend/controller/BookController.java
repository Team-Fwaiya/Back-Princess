package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.dto.BookDto;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// BookController는 책 정보에 대한 HTTP 요청을 처리하는 컨트롤러 계층
// REST API 형식으로 URI와 메서드를 매핑 -> 요청에 대한 응답을 JSON 형태로 반환

@RestController // 이 클래스가 REST API의 컨트롤러임을 선언 (ResponseBody + Controller 기능 결합)
@RequestMapping("/api/books") // "/api/books" 경로로 시작하는 모든 요청 처리
@RequiredArgsConstructor // 생성자 주입 방식으로 BookService 주입
@Tag(name = "04. 책 관리", description = "책 정보 조회, 등록, 수정, 삭제 API") // Swagger UI 문서화용 설명 태그
public class BookController {

    private final BookService bookService; // 비즈니스 로직을 처리할 서비스 계층

    // 책 등록 API [POST] /api/books

    @Operation(summary = "책 등록", description = "새로운 책 정보를 등록합니다.")
    @PostMapping
    public ApiResponse<Book> createBook(@RequestBody BookDto dto) {
        Book createdBook = bookService.saveBook(dto);
        //return ResponseEntity.ok(createdBook); // 200 OK + 저장된 Book JSON 반환
        return ApiResponse.onSuccess(SuccessCode.BOOK_CREATE_SUCCESS, createdBook);
    }


    // 전체 책 목록 조회 API [GET] /api/books

    @Operation(summary = "전체 책 목록 조회", description = "등록된 모든 책 정보를 조회합니다.")
    @GetMapping
    public ApiResponse<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        //return ResponseEntity.ok(books); // 200 OK + 책 리스트
        return ApiResponse.onSuccess(SuccessCode.BOOK_LIST_GET_SUCCESS, books);
    }

    // 특정 책 조회 API [GET] /api/books/{id}
    @Operation(summary = "특정 책 조회", description = "책 ID를 이용해 하나의 책 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponse<BookDto> getBook(@PathVariable Long id) {
        BookDto book = bookService.getBook(id);
        //return ResponseEntity.ok(book); // 200 OK + 단일 책 정보
        return ApiResponse.onSuccess(SuccessCode.BOOK_DETAIL_GET_SUCCESS, book);

        }


    // 책 수정 API [PUT] /api/books/{id}

    @Operation(summary = "책 정보 수정", description = "기존 책 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ApiResponse<Book> updateBook(@PathVariable Long id, @RequestBody BookDto dto) {
        Book updatedBook = bookService.updateBook(id, dto);
        //return ResponseEntity.ok(updatedBook); // 200 OK + 수정된 Book 정보
        return ApiResponse.onSuccess(SuccessCode.BOOK_UPDATE_SUCCESS, updatedBook);
    }


    // 책 삭제 API [DELETE] /api/books/{id}

    @Operation(summary = "책 삭제", description = "책 ID를 기준으로 해당 책을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        //return ResponseEntity.ok().build(); // 200 OK + 본문 없음
        return ApiResponse.onSuccess(SuccessCode.BOOK_DELETE_SUCCESS, "True");
    }
}
