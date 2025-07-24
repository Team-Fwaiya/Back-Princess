package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.dto.request.DiscussionCreateRequest;
import com.fwaiya.princess_backend.dto.response.DiscussionResponse;
import com.fwaiya.princess_backend.service.DiscussionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "07. 관리자 권한 기능 관리", description = " 관리자(1234@naver.com) 권한 기능을 사용할 수 있습니다. ")
@RequiredArgsConstructor
public class AdminController {
    private final DiscussionService discussionService;

    /** 토론방 등록 **/
    @PostMapping("/discussions")
    @Operation(summary = "토론방 등록", description = "관리자가 책 제목으로 토론방을 등록합니다.")
    public ResponseEntity<Object> createDiscussions(
            @RequestBody DiscussionCreateRequest discussionCreateRequest
    ) {
        discussionService.createDiscussion(discussionCreateRequest);
        return ResponseEntity.ok("토론방 등록을 완료하였습니다.");
    }

}
