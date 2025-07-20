package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.dto.response.DiscussionResponse;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.service.DiscussionService;
import com.fwaiya.princess_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
@Tag(name = "05. 토론방 관리", description = "토론방 CRUD")
@RequiredArgsConstructor
public class DiscussionController {
    private final UserService userService;
    private final DiscussionService discussionService;

    /** 토론방 전체 조회 **/
    @GetMapping
    @Operation(summary = "토론방 전체 조회", description = "로그인한 후 토론방 전체를 조회할 수 있습니다.")
    public ApiResponse<List<DiscussionResponse>> getAllDiscussions() {
        List<DiscussionResponse> discussions = discussionService.getAllDiscussions();
        return ApiResponse.onSuccess(SuccessCode.DISCUSSION_LIST_GET_SUCCESS, discussions);
    }

    /** 토론방 개별 조회 **/
    @GetMapping("/{id}")
    @Operation(summary = "토론방 개별 조회", description = "토론방 개별 조회를 하여 정보를 확인할 수 있습니다.")
    public ApiResponse<DiscussionResponse> getDiscussion(@PathVariable Long id){
        DiscussionResponse discussion= discussionService.getDiscussionById(id);
        return ApiResponse.onSuccess(SuccessCode.DISCUSSION_DETAIL_GET_SUCCESS, discussion);
    }
}
