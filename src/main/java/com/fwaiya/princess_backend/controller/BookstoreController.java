package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.dto.response.BookstoreResponseDto;
import com.fwaiya.princess_backend.service.BookstoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookstores")
@RequiredArgsConstructor
@Tag(name = "11. 서점", description = "서점 관련 API")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @GetMapping("/nearby")
    @Operation(summary = "근처 서점 조회", description = "위치 정보를 바탕으로 근처 서점 5개를 조회합니다.")
    public ApiResponse<List<BookstoreResponseDto>> getNearbyBookstores(
            @RequestParam("location") String location) {

        List<BookstoreResponseDto> bookstores = bookstoreService.getNearbyBookstores(location);
        return ApiResponse.onSuccess(SuccessCode.OK, bookstores);
    }
}