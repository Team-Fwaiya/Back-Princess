package com.fwaiya.princess_backend.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @Schema(description = "사용자 ID", example = "1234@naver.com")
    private String userId; // (=username)

    @Schema(description = "비밀번호", example = "yaaan7")
    private String password;
}