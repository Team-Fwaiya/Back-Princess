package com.fwaiya.princess_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WantCreateRequest {

    @NotBlank(message = "책 제목은 필수입니다.")
    private String bookTitle;

    @NotBlank(message = "저자는 필수입니다.")
    private String author;

    @NotBlank(message = "장르는 필수입니다.")
    private String genre;

    @NotBlank(message = "메모는 필수입니다.")
    private String memo;
}
