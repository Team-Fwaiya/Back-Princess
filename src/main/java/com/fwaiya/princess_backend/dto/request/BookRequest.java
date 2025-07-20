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
    @Schema(description = "책 제목", example = "나미야 잡화점의 기적")
    private String title;

    @NotBlank
    @Schema(description = "저자", example = "히가시노 게이고")
    private String author;

    @NotNull
    @Schema(description = "장르 (enum)", example = "fiction", implementation = Genre.class)
    private Genre genre;

    @Schema(description = "책 표지 이미지 URL", example = "https://s3.bucket.com/image.jpg")
    private String coverImageUrl;

    @Schema(description = "해시태그", example = "#감동#힐링")
    private String hashtags;
}