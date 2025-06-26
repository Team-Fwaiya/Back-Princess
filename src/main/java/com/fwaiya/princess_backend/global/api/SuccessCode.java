package com.fwaiya.princess_backend.global.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode { // 성공
    // Common
    OK(HttpStatus.OK, "COMMON_200", "Success"),
    CREATED(HttpStatus.CREATED, "COMMON_201", "Created"),

    // User
    USER_LOGIN_SUCCESS(HttpStatus.CREATED, "USER_201", "회원가입& 로그인이 완료되었습니다."),
    USER_REISSUE_SUCCESS(HttpStatus.OK, "USER_200", "토큰 재발급이 완료되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_200", "회원탈퇴가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    // 응답 코드 상세 정보 return
    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
