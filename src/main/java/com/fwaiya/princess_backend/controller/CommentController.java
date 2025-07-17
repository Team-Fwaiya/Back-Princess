package com.fwaiya.princess_backend.controller;

import com.fwaiya.princess_backend.dto.request.CommentCreateRequest;
import com.fwaiya.princess_backend.dto.request.DiscussionCreateRequest;
import com.fwaiya.princess_backend.dto.response.CommentResponse;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions/{id}")
@Tag(name = "06. 댓글 관리", description = "댓글 등록, 댓글 목록 조회 기능")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /** 댓글 등록 기능**/
    @PostMapping("/comments")
    @Operation(summary = "댓글 등록", description = "사용자가 토론방에 댓글을 등록합니다.")
    public ResponseEntity<Object> createComments(
            @PathVariable Long id,
            @RequestBody CommentCreateRequest commentCreateRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        commentService.createComment( id ,commentCreateRequest , customUserDetails);
        return ResponseEntity.ok("댓글 등록을 완료하였습니다.");
    }

    /** 토론방 별 댓글 목록 조회 기능**/
    @GetMapping("/comments")
    @Operation(summary = "댓글 목록 조회", description = "토론방의 댓글 목록을 조회합니다.")
    public ResponseEntity<Object> getComments(
            @PathVariable Long id
    ){
        List<CommentResponse> comments = commentService.getAllComments(id);

        return ResponseEntity.ok(comments);
    }



}
