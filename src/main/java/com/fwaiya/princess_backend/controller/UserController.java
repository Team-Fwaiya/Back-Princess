package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.request.ProfileImageUpdateRequest;
import com.fwaiya.princess_backend.dto.request.WantCreateRequest;
import com.fwaiya.princess_backend.dto.response.UserInfoResponse;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.global.constant.ProfileImageConstants;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.service.S3Service;
import com.fwaiya.princess_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** * 사용자 정보 관리하는 컨트롤러 클래스 * 사용자 CRUD** @author yaaan7 * @since 2025-06-29 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "03. 사용자 관리", description = "사용자 CRUD , 읽고 싶은 책 등록")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;

    /**
     * 회원탈퇴* *@param username(=userId)
     */
    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴", description = "로그인한 사용자의 회원탈퇴를 진행합니다.")
    public ApiResponse<Object> withdraw(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        userService.withdraw(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.USER_DELETE_SUCCESS, "True");
    }

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    public ApiResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.USER_INFO_GET_SUCCESS, userInfoResponse);
    }

    /**
     * 읽고 싶은 책 등록
     **/
    @PostMapping("/want")
    @Operation(summary = "읽고 싶은 책 등록", description = "로그인한 사용자가 읽고 싶은 책을 등록합니다.")
    public ApiResponse<String> createWant(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody WantCreateRequest wantCreateRequest
    ) {
        User user = userService.findByUsername(customUserDetails.getUsername());
        userService.createWant(wantCreateRequest, user);
        return ApiResponse.onSuccess(SuccessCode.USER_WANT_POST_SUCCESS, "True");
    }

    /**
     * 읽고 싶은 책 삭제
     **/
    @DeleteMapping("/want/{wantID}")
    @Operation(summary = "읽고 싶은 책 삭제", description = "로그인한 사용자의 특정 읽고 싶은 책을 삭제합니다.")
    public ApiResponse<String> deleteWant(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long wantID
    ) {
        User user = userService.findByUsername(customUserDetails.getUsername());
        userService.deleteWant(wantID, user);
        return ApiResponse.onSuccess(SuccessCode.USER_WANT_DELETE_SUCCESS, "True");
    }

    /**
     * 프로필 사진 등록
     **/
    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 사진 등록", description = "로그인한 사용자의 프로필 사진을 등록합니다.")
    public ApiResponse<String> updateProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("file") MultipartFile file
    ) {
        User user = userService.findByUsername(customUserDetails.getUsername());
        String imageUrl = s3Service.uploadProfileImage(file);
        userService.updateProfile(imageUrl, user);
        return ApiResponse.onSuccess(SuccessCode.USER_PROFILE_IMAGE_UPDATE_SUCCESS, imageUrl);
    }

}