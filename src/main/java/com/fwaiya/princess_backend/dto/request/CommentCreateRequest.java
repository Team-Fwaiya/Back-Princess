package com.fwaiya.princess_backend.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @Lob
    @NotBlank(message = "댓글 내용 입력은 필수입니다.")
    private String content;

}
