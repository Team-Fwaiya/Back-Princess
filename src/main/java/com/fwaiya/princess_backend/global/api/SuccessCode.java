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

    ALREADY_USED_NICKNAME(HttpStatus.OK, "USER_2012", "이미 사용 중인 닉네임입니다."),
    ALREADY_USED_USERID(HttpStatus.OK, "USER_2011", "이미 사용 중인 아이디 입니다."),

    // User_Want
    USER_WANT_POST_SUCCESS(HttpStatus.OK, "USER_2002", "읽고 싶은 책 등록이 완료되었습니다."),
    USER_WANT_DELETE_SUCCESS(HttpStatus.OK, "USER_2003", "읽고 싶은 책 삭제가 완료되었습니다."),
    // User_Profile
    USER_PROFILE_IMAGE_UPDATE_SUCCESS(HttpStatus.OK, "USER_2004", "프로필 이미지 변경이 완료되었습니다."),
    USER_PROFILE_IMAGE_LIST_GET_SUCCESS(HttpStatus.OK, "USER_2005", "프로필 이미지 목록 조회 성공"),

    // Book
    BOOK_CREATE_SUCCESS(HttpStatus.CREATED, "BOOK_201", "책 등록이 완료되었습니다."),
    BOOK_LIST_GET_SUCCESS(HttpStatus.OK, "BOOK_2001", "책 전체 목록 조회가 완료되었습니다."),
    BOOK_DETAIL_GET_SUCCESS(HttpStatus.OK, "BOOK_2002", "특정 책 조회가 완료되었습니다."),
    BOOK_UPDATE_SUCCESS(HttpStatus.OK, "BOOK_2003", "책 수정이 완료되었습니다."),
    BOOK_DELETE_SUCCESS(HttpStatus.OK, "BOOK_2004", "책 삭제가 완료되었습니다."),

    // Comment
    COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "COMMENT_201", "댓글 등록이 완료되었습니다."),
    COMMENT_LIST_BY_DISCUSSION_GET_SUCCESS(HttpStatus.OK, "COMMENT_2001", "토론방 별 댓글 목록 조회가 완료되었습니다."),

    // Discussion
    DISCUSSION_LIST_GET_SUCCESS(HttpStatus.OK, "DISCUSSION_2001", "토론방 전체 조회가 완료되었습니다."),
    DISCUSSION_DETAIL_GET_SUCCESS(HttpStatus.OK, "DISCUSSION_2002", "토론방 개별 조회가 완료되었습니다."),

    // Quote
    QUOTE_GET_SUCCESS(HttpStatus.OK, "QUOTE_2001", "명언 반환이 완료되었습니다."),

    // ReadingLog
    READING_LOG_CREATE_SUCCESS(HttpStatus.CREATED, "READING_201", "독서록 등록이 완료되었습니다."),
    READING_LOG_LIST_GET_SUCCESS(HttpStatus.OK, "READING_2001", "내 독서록 목록 조회가 완료되었습니다."),
    READING_LOG_DETAIL_GET_SUCCESS(HttpStatus.OK, "READING_2002", "독서록 상세 조회가 완료되었습니다."),
    READING_LOG_UPDATE_SUCCESS(HttpStatus.OK, "READING_2003", "독서록 수정이 완료되었습니다."),
    READING_LOG_DELETE_SUCCESS(HttpStatus.OK, "READING_2004", "독서록 삭제가 완료되었습니다."),


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