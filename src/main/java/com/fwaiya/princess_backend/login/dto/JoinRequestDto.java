package com.fwaiya.princess_backend.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")  // 로그인 -> 회원가입으로 수정
public class JoinRequestDto {

    @NotBlank(message = "Id는 필수입니다.")
    @Schema(description = "사용자 ID", example = "1234@naver.com")
    private String userId;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Schema(description = "닉네임", example = "yaaan")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(description = "비밀번호", example = "yaaan7")
    private String password;

    @NotNull(message = "궁전 주소는 필수입니다.")
    @Schema(description = "주소", example = "경기도 고양시")
    private String address;

//    @NotNull(message = "생년월일은 필수입니다.")  // @NotBlank -> @NotNull로 변경
//    @Schema(description = "생년월일", example = "2025-01-01")
//    private LocalDate birthDate;
}