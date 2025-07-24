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
 * 사용자가 독서록을 작성하거나 수정할 때 요청 본문으로 전달하는 DTO 클래스
 * 책 정보를 포함하여 한 번의 요청으로 독서록과 책을 동시에 등록
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "독서록 작성 요청 예시", example = """
{
  "book": {
    "title": "이기적 유전자",
    "author": "리처드 도킨스",
    "genre": "science",
    "coverImageUrl": "https://s3.bucket.com/selfish_gene.jpg",
    "hashtags": "#진화#생물학#과학명저"
  },
  "oneLineReview": "진화론에 대한 시야가 넓어졌습니다.",
  "content": "이 책은 생물학을 넘어서 인간 사회와 사고방식까지 통찰하게 해줍니다. 어렵지만 꼭 읽어야 할 과학 명저입니다.",
  "rating": 5
}
""")
public class ReadingLogRequest {

    /**
     * 독서록을 작성할 책의 정보
     * - BookRequest 객체로 전달되며, 제목, 저자, 장르 등의 정보 포함
     * - 해당 책이 DB에 없을 경우, 서버에서 자동으로 등록
     */
    @NotNull(message = "책 정보는 필수입니다.")
    private BookRequest book;

    @NotBlank(message = "한 줄 평은 필수입니다.")
    private String oneLineReview;

    @Lob
    @NotBlank(message = "감상 내용은 필수입니다.")
    private String content;

    @NotNull(message = "평점은 필수입니다.")
    @Min(0)
    @Max(5)
    private Integer rating;
}
