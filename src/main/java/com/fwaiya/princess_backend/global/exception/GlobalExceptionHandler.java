package com.fwaiya.princess_backend.global.exception;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.ReasonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(GeneralException e) {
        ReasonDto reason = e.getReason();

        return ResponseEntity
                .status(reason.getHttpStatus())
                .body(ApiResponse.onFailure(reason, null));
    }
}

