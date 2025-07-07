package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.Book;
import com.fwaiya.princess_backend.dto.BookDto;
import com.fwaiya.princess_backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


//  BookService는 책 등록, 조회, 수정, 삭제와 같은 비즈니스 로직을 처리하는 서비스 계층
// Repository를 통해 DB에 접근, Controller와 Repository 사이에서 중간 역할을 수행

@Service // 스프링이 이 클래스를 서비스 컴포넌트로 인식하게 함
@RequiredArgsConstructor // 생성자를 통한 의존성 주입 자동 처리
public class BookService {

    private final BookRepository bookRepository;

    // 책 등록 메서드
    //  BookDto를 Book 엔티티로 변환한 후, DB에 저장
    public Book saveBook(BookDto dto) {
        Book newBook = dto.toEntity();
        return bookRepository.save(newBook);
    }


    // 전체 책 목록 조회
    // Repository에서 모든 Book 엔티티를 조회한 후, 클라이언트에 반환하기 위해 DTO 리스트로 변환
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDto::fromEntity) // 각 Book → BookDto로 변환
                .collect(Collectors.toList());
    }


    // 특정 책 조회
    // 해당 ID에 해당하는 Book 엔티티가 없으면 예외 발생
    // 있으면 DTO로 변환 후 반환
    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다. ID: " + id));
        return BookDto.fromEntity(book);
    }


    // 책 정보 수정
    // 기존 데이터를 조회한 뒤, 전달된 값을 기반으로 새 Book 객체를 만듦
    public Book updateBook(Long id, BookDto dto) {
        Book original = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다. ID: " + id));

        Book updated = Book.builder()
                .id(original.getId()) // ID는 유지
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .genre(dto.getGenre())
                .coverImageUrl(dto.getCoverImageUrl())
                .hashtags(dto.getHashtags())
                .build();

        return bookRepository.save(updated); // save로 업데이트 처리
    }


    // 책 삭제
    // 해당 책이 존재하지 않으면 예외 발생
    // 존재하면 삭제
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 책이 존재하지 않습니다. ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}
