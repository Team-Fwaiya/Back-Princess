package com.fwaiya.princess_backend.dto.response;


import com.fwaiya.princess_backend.domain.Discussion;
import com.fwaiya.princess_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiscussionResponse {
    private String bookTitle;
    private String bookAuthor;
    private String bookCoverImageUrl;

    public static DiscussionResponse from(Discussion discussion) {
        return new DiscussionResponse(
                discussion.getBook().getTitle(),
                discussion.getBook().getAuthor(),
                discussion.getBook().getCoverImageUrl()
        );
    }
}
