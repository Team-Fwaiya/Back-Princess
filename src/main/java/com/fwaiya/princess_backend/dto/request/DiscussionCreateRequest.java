package com.fwaiya.princess_backend.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionCreateRequest {

    private String title; // 토론방 제목

    @NotBlank(message = "책 제목은 필수입니다.")
    private String bookTitle;

}
