package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.dto.response.LibraryResponseDto;
import com.fwaiya.princess_backend.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
@RequiredArgsConstructor
@Tag(name = "10. 도서관 조회", description = "도서관 조회 API")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/nearby")
    @Operation(summary = "근처 도서관 조회", description = "시와 구를 바탕으로 근처 도서관 5개를 조회합니다.")
    public ApiResponse<List<LibraryResponseDto>> getNearbyLibraries(
            @RequestParam("city") String city,
            @RequestParam("district") String district) {

        List<LibraryResponseDto> libraries = libraryService.getNearbyLibraries(city, district);
        return ApiResponse.onSuccess(SuccessCode.OK, libraries);
    }
}