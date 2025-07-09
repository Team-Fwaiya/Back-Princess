package com.fwaiya.princess_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileImageUpdateRequest {
    @NotBlank
    @Schema(description = "프로필 사진 경로", example = "https://s3.bucket.com/profile/default1.png")
    private String imagePath;
}

