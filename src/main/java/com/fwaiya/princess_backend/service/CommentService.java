package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.Comment;
import com.fwaiya.princess_backend.domain.Discussion;
import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.request.CommentCreateRequest;
import com.fwaiya.princess_backend.dto.response.CommentResponse;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiscussionService discussionService;
    private final UserService userService;

    /** 댓글 등록 기능**/
    @Transactional
    public void createComment(Long discussionId, CommentCreateRequest request, CustomUserDetails customUserDetails) {
        // 유저 확인
        User user = userService.findByUsername(customUserDetails.getUsername());
        // 토론방 id 확인
        Discussion discussion = discussionService.getActiveDiscussion(discussionId);

        Comment comment = Comment.builder()
                .discussion(discussion)
                .user(user)
                .content(request.getContent())
                .build();
        // 토론방의 댓글 리스트로 등록
        discussion.addComment(comment);
        commentRepository.save(comment);
    }

    /** 댓글 목록 조회 기능**/
    @Transactional
    public List<CommentResponse> getAllComments(Long discussionId){
        Discussion discussion = discussionService.getActiveDiscussion(discussionId);
        return discussion.getComments().stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

}
