package com.fwaiya.princess_backend.login.controller;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.login.dto.LoginRequestDto;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.login.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/swagger")
    @Operation(summary = "Swagger 로그인", description = "username, password로 JWT 토큰을 발급받습니다.")
    public ApiResponse<String> login(@RequestBody LoginRequestDto dto) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());

            return ApiResponse.onSuccess(SuccessCode.USER_LOGIN_SUCCESS, jwt);

        } catch (AuthenticationException e) {
            return ApiResponse.onFailure(ErrorCode.INVALID_CREDENTIALS, null);
        }
    }
}
