package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.repository.UserRepository;
import com.fwaiya.princess_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //nickname 반환
    @GetMapping
    @Operation(summary = "닉네임 반환", description = "로그인한 사용자의 닉네임을 반환합니다.")
    public ApiResponse<?> getNickname(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String nickname = userService.getNickname(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.USER_NICKNAME_GET_SUCCESS, nickname);
    }
}
