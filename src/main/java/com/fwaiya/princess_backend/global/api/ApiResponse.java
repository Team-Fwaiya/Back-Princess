package com.fwaiya.princess_backend.global.api;

/*public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "SUCCESS", data);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // getter들
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }

}*/

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> { // API 응답

    private final Boolean isSuccess; // 성공 여부
    private final String code; // 응답 코드
    private final String message; // 메시지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result; // 응답 데이터

    //성공
    public static <T> ApiResponse<T> onSuccess(BaseCode code, T result) {
        return new ApiResponse<>(true, code.getReason().getCode(), code.getReason().getMessage(), result);
    }

    //실패
    public static <T> ApiResponse<T> onFailure(BaseCode code, T data) {
        return new ApiResponse<>(false, code.getReason().getCode(), code.getReason().getMessage(), data);
    }
}