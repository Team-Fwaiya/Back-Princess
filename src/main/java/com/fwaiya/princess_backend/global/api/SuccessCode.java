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
    USER_JOIN_SUCCESS(HttpStatus.CREATED, "USER_201", "회원가입이 완료되었습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.CREATED, "USER_201", "로그인이 완료되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_200", "회원탈퇴가 완료되었습니다."),

    USER_INFO_GET_SUCCESS(HttpStatus.OK, "USER_2001", "회원정보 조회가 완료되었습니다."),
    USER_WANT_POST_SUCCESS(HttpStatus.OK, "USER_2002", "읽고 싶은 책 등록이 완료되었습니다."),
    USER_WANT_DELETE_SUCCESS(HttpStatus.OK, "USER_2003", "읽고 싶은 책 삭제가 완료되었습니다."),
    USER_PROFILE_IMAGE_UPDATE_SUCCESS(HttpStatus.OK, "USER_2004", "프로필 이미지 변경이 완료되었습니다."),
    USER_PROFILE_IMAGE_LIST_GET_SUCCESS(HttpStatus.OK, "USER_2005", "프로필 이미지 목록 조회 성공"),

    // File Upload
    IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, "FILE_2001", "이미지 업로드가 완료되었습니다."),
    BOOK_COVER_UPLOAD_SUCCESS(HttpStatus.OK, "FILE_2002", "책 표지 이미지 업로드가 완료되었습니다.");


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