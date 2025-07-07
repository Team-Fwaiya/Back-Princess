package com.fwaiya.princess_backend.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;;

@Getter
@Setter
@Schema(description = "로그인 요청 DTO")
public class JoinRequestDto {

    @Schema(description = "사용자 ID", example = "1234@naver.com")
    private String userId;

    @Schema(description = "닉네임", example = "yaaan")
    private String nickname;

    @Schema(description = "비밀번호", example = "yaaan7")
    private String password;

    @Schema(description = "생년월일", example = "2025-01-01")
    private LocalDate birthDate;

}
