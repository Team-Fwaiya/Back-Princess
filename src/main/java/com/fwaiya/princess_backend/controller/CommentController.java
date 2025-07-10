package com.fwaiya.princess_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discussions/{id}")
@Tag(name = "06. 댓글 관리", description = "댓글 등록, 댓글 목록 조회 기능")
@RequiredArgsConstructor
public class CommentController {
    /** 댓글 등록 기능**/

    /** 댓글 목록 조회 기능**/

}
