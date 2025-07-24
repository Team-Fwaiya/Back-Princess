package com.fwaiya.princess_backend.dto.request;

import com.fwaiya.princess_backend.global.constant.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "장르는 필수입니다.")
    private Genre genre;
}
