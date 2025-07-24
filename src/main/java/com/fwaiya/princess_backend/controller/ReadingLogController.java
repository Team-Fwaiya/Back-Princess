package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.dto.request.ReadingLogRequest;
import com.fwaiya.princess_backend.dto.response.ReadingLogResponse;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.service.ReadingLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReadingLogController
 * 독서록 등록, 조회, 수정, 삭제 API를 제공하는 컨트롤러 클래스
 * 모든 기능은 로그인한 사용자 기준으로 동작하며, 본인 독서록만 관리할 수 있도록 제한
 */
@RestController
@RequestMapping("/api/reading-logs")
@Tag(name = "09. 독서록 관리", description = "독서록 등록, 조회, 수정, 삭제 API")
@RequiredArgsConstructor
public class ReadingLogController {

    private final ReadingLogService readingLogService;

    /**
     * 독서록 등록
     * - 로그인한 사용자 -> 새로운 독서록 작성
     * - 요청 본문: ReadingLogRequest
     */
    @PostMapping
    @Operation(summary = "독서록 등록", description = "로그인한 사용자가 독서록을 작성합니다.")
    public ApiResponse<String> createReadingLog(
            @RequestBody ReadingLogRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        readingLogService.createReadingLog(request, customUserDetails);
        return ApiResponse.onSuccess(SuccessCode.READING_LOG_CREATE_SUCCESS, "True");
    }

    /**
     * 내 독서록 목록 조회
     * - 로그인한 사용자가 작성한 모든 독서록 조회
     */
    @GetMapping("/my")
    @Operation(summary = "내 독서록 목록 조회", description = "로그인한 사용자의 독서록 목록을 조회합니다.")
    public ApiResponse<List<ReadingLogResponse>> getMyReadingLogs(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        List<ReadingLogResponse> response = readingLogService.getMyReadingLogs(customUserDetails);
        return ApiResponse.onSuccess(SuccessCode.READING_LOG_LIST_GET_SUCCESS, response);
    }

    /**
     * 독서록 상세 조회
     * - 로그인한 사용자가 작성한 단일 독서록 조회
     * - 다른 사용자의 독서록은 조회 불가
     */
    @GetMapping("/{readingLogId}")
    @Operation(summary = "내 독서록 상세 조회", description = "내가 작성한 단일 독서록을 조회합니다.")
    public ApiResponse<ReadingLogResponse> getReadingLogById(
            @PathVariable Long readingLogId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ReadingLogResponse response = readingLogService.getReadingLogById(readingLogId, customUserDetails);
        return ApiResponse.onSuccess(SuccessCode.READING_LOG_DETAIL_GET_SUCCESS, response);
    }

    /**
     * 독서록 수정
     */
    @PutMapping("/{readingLogId}")
    @Operation(summary = "독서록 수정", description = "로그인한 사용자가 작성한 독서록을 수정합니다.")
    public ApiResponse<String> updateReadingLog(
            @PathVariable Long readingLogId,
            @RequestBody ReadingLogRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        readingLogService.updateReadingLog(readingLogId, request, customUserDetails);
        return ApiResponse.onSuccess(SuccessCode.READING_LOG_UPDATE_SUCCESS, "True");
    }

    /**
     * 독서록 삭제
     * - 로그인한 사용자가 작성한 독서록 삭제
     */
    @DeleteMapping("/{readingLogId}")
    @Operation(summary = "독서록 삭제", description = "로그인한 사용자가 작성한 독서록을 삭제합니다.")
    public ApiResponse<String> deleteReadingLog(
            @PathVariable Long readingLogId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        readingLogService.deleteReadingLog(readingLogId, customUserDetails);
        return ApiResponse.onSuccess(SuccessCode.READING_LOG_DELETE_SUCCESS, "True");
    }


}
