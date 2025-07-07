package com.fwaiya.princess_backend.global.api;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ReasonDto implements BaseCode {

    private HttpStatus httpStatus;
    private String code;
    private String message;

    @Override
    public ReasonDto getReason() {
        return this;
    }
}
