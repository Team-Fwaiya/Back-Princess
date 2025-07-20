package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "이미지 업로드", description = "프로필 이미지, 책 표지 이미지 업로드 API")
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @Operation(summary = "프로필 이미지 업로드", description = "사용자 프로필 이미지를 업로드합니다.")
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file) {

        String imageUrl = s3Service.uploadProfileImage(file);

        Map<String, String> result = new HashMap<>();
        result.put("imageUrl", imageUrl);

        return ApiResponse.onSuccess(SuccessCode.USER_PROFILE_IMAGE_UPDATE_SUCCESS, result);
    }

    @Operation(summary = "책 표지 이미지 업로드", description = "독서록용 책 표지 이미지를 업로드합니다.")
    @PostMapping(value = "/book-cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, String>> uploadBookCoverImage(
            @RequestParam("file") MultipartFile file) {

        String imageUrl = s3Service.uploadBookCoverImage(file);

        Map<String, String> result = new HashMap<>();
        result.put("imageUrl", imageUrl);

        return ApiResponse.onSuccess(SuccessCode.OK, result);
    }
}