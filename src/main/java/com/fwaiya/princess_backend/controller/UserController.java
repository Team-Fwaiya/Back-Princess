package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.request.ProfileImageUpdateRequest;
import com.fwaiya.princess_backend.dto.request.WantCreateRequest;
import com.fwaiya.princess_backend.dto.response.UserInfoResponse;
import com.fwaiya.princess_backend.global.constant.ProfileImageConstants;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Object> withdraw(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        userService.withdraw(customUserDetails.getUsername());
        return ResponseEntity.ok("회원 탈퇴 완료하였습니다");
    }

    /** *사용자 정보 조회*/
    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(customUserDetails.getUsername());
        return ResponseEntity.ok(userInfoResponse);
    }

    /** 읽고 싶은 책 등록 **/
    @PostMapping("/want")
    @Operation(summary = "읽고 싶은 책 등록", description = "로그인한 사용자가 읽고 싶은 책을 등록합니다.")
    public ResponseEntity<String> createWant(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody WantCreateRequest wantCreateRequest
    ){
        User user = userService.findByUsername(customUserDetails.getUsername());
        userService.createWant( wantCreateRequest,user );
        return ResponseEntity.ok("읽고 싶은 책 등록 완료하였습니다.");
    }

    /** 읽고 싶은 책 삭제 **/
    @DeleteMapping("/want/{wantID}")
    @Operation(summary = "읽고 싶은 책 삭제", description = "로그인한 사용자의 특정 읽고 싶은 책을 삭제합니다.")
    public ResponseEntity<Object> deleteWant(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long wantID
    ){
        User user = userService.findByUsername(customUserDetails.getUsername());
        userService.deleteWant(wantID, user);
        return ResponseEntity.ok("삭제 완료하였습니다.");
    }

    /** 프로필 사진 수정**/
    @PatchMapping("/profile")
    @Operation(summary = "프로필 사진 변경", description = "로그인한 사용자의 프로필 사진을 8가지 중 하나로 변경합니다.")
    public ResponseEntity<Object> updateProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody ProfileImageUpdateRequest profileImageUpdateRequest
    ){
        User user = userService.findByUsername(customUserDetails.getUsername());
        userService.updateProfile(profileImageUpdateRequest.getImagePath(), user);

        return ResponseEntity.ok("프로필 사진 변경 완료하였습니다.");
    }

    /** 프로필 사진 목록 조회 **/
    @GetMapping("/profiles")
    @Operation(summary = "프로필 사진 목록 조회", description="고정된 8개의 프로필 사진 목록을 반환합니다.")
    public ResponseEntity<List<String>> getProfiles(){
        return ResponseEntity.ok(ProfileImageConstants.FIXED_IMAGES);
    }
}
