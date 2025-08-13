package com.fwaiya.princess_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * [ReadingLogRequest]
 * 독서록 작성/수정 요청 DTO
 * - multipart/form-data 전송 시: @RequestPart("readingLog") 로 이 객체를 JSON으로
 * - 표지 이미지는 별도 파트 coverImage(파일)로 전송.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ReadingLogRequest",
        description = "독서록 작성 요청 바디(JSON). 파일은 별도 파트 coverImage로 전송",
        example = """
{
  "book": {
    "title": "이기적 유전자",
    "author": "리처드 도킨스",
    "genre": "science",
    "coverImageUrl": "https://... (파일이 있으면 서버에서 이 값은 무시)",
    "hashtags": "#진화#생물학#과학명저"
  },
  "content": "이 책은 생물학을 넘어서 인간 사회와 사고방식까지 통찰하게 해줍니다.",
  "rating": 5
}
"""
)
public class ReadingLogRequest {

    @NotNull(message = "책 정보는 필수입니다.")
    @Schema(
            description = "책 정보(JSON). 파일 업로드가 있을 경우 book.coverImageUrl은 무시됨",
            implementation = BookRequest.class
    )
    private BookRequest book;


    @Lob
    @NotBlank(message = "감상 내용은 필수입니다.")
    @Schema(
            description = "독서록 본문",
            example = "이 책은 생물학을 넘어서 인간 사회와 사고방식까지 통찰하게 해줍니다."
    )
    private String content;

    @NotNull(message = "평점은 필수입니다.")
    @Min(0) @Max(5)
    @Schema(
            description = "평점(0~5)",
            minimum = "0", maximum = "5",
            example = "5"
    )
    private Integer rating;
}