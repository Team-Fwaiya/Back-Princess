package com.fwaiya.princess_backend.login.controller;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
import com.fwaiya.princess_backend.login.dto.JoinResponseDto;
import com.fwaiya.princess_backend.login.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** *회원가입 컨트롤러 클래스 *회원 가입 기능 제공 *** @author yaaan7 @since 2025-06-26 */
@Slf4j
@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "01. 회원가입", description = "회원가입 API")
public class JoinController {
    private final JoinService joinService;

    @Operation(summary = "회원가입", description = "사용자 정보를 받아 회원가입을 진행합니다.")
    @PostMapping("/join")
    public ApiResponse<JoinResponseDto> joinProcess(
            @RequestBody JoinRequestDto joinRequestDto
    ) {
        JoinResponseDto response = joinService.joinProcess(joinRequestDto);
        return ApiResponse.onSuccess(SuccessCode.USER_JOIN_SUCCESS, response);
    }
}
