package com.fwaiya.princess_backend.global.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode { // 실패

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 서버 개발자에게 문의하세요."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "USER_4011", "자격 증명이 유효하지 않습니다."),
    UNAUTHORIZED_READING_LOG_ACCESS(HttpStatus.FORBIDDEN, "READING_4003", "본인의 독서록만 조회할 수 있습니다."),
    UNAUTHORIZED_READING_LOG_UPDATE(HttpStatus.FORBIDDEN, "READING_4004", "본인의 독서록만 수정할 수 있습니다."),
    UNAUTHORIZED_READING_LOG_DELETE(HttpStatus.FORBIDDEN, "READING_4005", "본인의 독서록만 삭제할 수 있습니다."),


    ID_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "AUTH_4011", "아이디 또는 비밀번호가 일치하지 않습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"AUTH_4001", "로그인에 실패했습니다."),

    // Jwt
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT_4011", "Authorization 헤더가 없거나 형식이 올바르지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_4012", "토큰 유효기간이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "JWT_4031", "유효하지 않은 토큰입니다."),
    TOKEN_NO_AUTH(HttpStatus.FORBIDDEN, "JWT_4031", "권한 정보가 없는 토큰입니다."),

    // Profile & S3
    INVALID_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, "USER_4001", "허용되지 않은 이미지 파일입니다. (jpg, png, gif 형식, 5MB 이하만 가능)"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_5001", "파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_5002", "파일 삭제에 실패했습니다."),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, "FILE_4001", "지원하지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "FILE_4002", "파일 크기가 제한을 초과했습니다. (최대 5MB)"),

    // Book
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_4001", "해당 책 정보를 찾을 수 없습니다."),
    BOOK_TITLE_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_4002", "해당 제목의 책이 존재하지 않습니다."),

    // ReadingLog
    READING_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "READING_4002", "해당 독서록을 찾을 수 없습니다."),


    // Discussion
    DISCUSSION_NOT_FOUND(HttpStatus.NOT_FOUND, "DISCUSSION_4001", "활성화된 토론방이 존재하지 않습니다."),

    // Quote
    QUOTE_FETCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "QUOTE_5001", "명언을 불러오는 데 실패했습니다."),

    // User_Want
    WANT_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4003", "해당 읽고 싶은 책 정보를 찾을 수 없습니다."),
    WANT_BOOK_NOT_FOUND_OR_UNAUTHORIZED(HttpStatus.NOT_FOUND, "USER_4004", "삭제할 책이 존재하지 않거나 권한이 없습니다.");


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