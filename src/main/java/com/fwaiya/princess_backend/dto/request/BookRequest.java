package com.fwaiya.princess_backend.dto.request;


import com.fwaiya.princess_backend.global.constant.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [BookRequest]
 * 책 등록/수정 시 사용되는 요청 DTO
 */
@Getter
@NoArgsConstructor

public class BookRequest {

    @NotBlank
    @Schema(description = "책 제목", example = "넛지")
    private String title;

    @NotBlank
    @Schema(description = "저자", example = "리처드 탈러, 캐스 선스타인")
    private String author;

    @NotNull
    @Schema(description = "장르 (enum)", example = "humanities", implementation = Genre.class)
    private Genre genre;


    @Schema(description = "해시태그", example = "#행동경제학#넛지이론#선택설계#심리학")
    private String hashtags;
}