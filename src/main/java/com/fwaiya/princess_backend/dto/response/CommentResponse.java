package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.Comment;
import com.fwaiya.princess_backend.global.constant.ReadingLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
    // 댓글 content,user의 프로필, 닉네임, 리딩레벨
    public String content;
    public String nickname;
    public String imagePath;
    public String readingLevel;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getContent(),
                comment.getUser().getNickname(),
                comment.getUser().getImagePath(),
                comment.getUser().getReadingLevel().getKoreanName()
        );
    }
}
