package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.WantToRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 읽고 싶은 책 응답 DTO **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WantCreateResponse {
    private Long id;
    private String bookTitle;
    private String author;
    private String genre;

    // DTO로 변환
    public static WantCreateResponse from(WantToRead wantToRead) {
        return new WantCreateResponse(
                wantToRead.getId(),
                wantToRead.getBookTitle(),
                wantToRead.getAuthor(),
                wantToRead.getGenre()
        );
    }

}
