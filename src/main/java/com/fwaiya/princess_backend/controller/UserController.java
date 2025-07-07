package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.response.UserInfoResponse;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** * 사용자 정보 관리하는 컨트롤러 클래스 * 사용자 CRUD** @author yaaan7 * @since 2025-06-29 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "03. 사용자 관리", description = "사용자 CRUD , 읽고 싶은 책 등록")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /** *회원탈퇴* *@param username(=userId)*/
    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴", description = "로그인한 사용자의 회원탈퇴를 진행합니다.")
    public ApiResponse<?> withdraw(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        userService.withdraw(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.USER_DELETE_SUCCESS, null);
    }

    /** *사용자 정보 조회*/
    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    public ApiResponse<?> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.USER_NICKNAME_GET_SUCCESS, userInfoResponse);
    }
}
