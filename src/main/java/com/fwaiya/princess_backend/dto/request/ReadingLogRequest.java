package com.fwaiya.princess_backend.dto.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReadingLogRequest
 * 독서록 등록 및 수정 시 사용하는 요청 DTO 클래스입니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingLogRequest {

    /** 책 ID */
    @NotNull(message = "책 ID는 필수입니다.")
    private Long bookId;

    /** 한 줄 평 */
    @NotBlank(message = "한 줄 평 입력은 필수입니다.")
    private String oneLineReview;

    /** 감상 내용 */
    @Lob
    @NotBlank(message = "감상 내용 입력은 필수입니다.")
    private String content;

    /** 평점: 0~5 */
    @NotNull(message = "평점 입력은 필수입니다.")
    @Min(0)
    @Max(5)
    private Integer rating;
}
