package com.fwaiya.princess_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileImageUpdateRequest {
    @NotBlank
    private String imagePath;
}

