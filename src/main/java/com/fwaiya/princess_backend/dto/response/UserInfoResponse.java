package com.fwaiya.princess_backend.dto.response;

import com.fwaiya.princess_backend.domain.ReadingLog;
import com.fwaiya.princess_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/** User Service -> Controller , Controller -> Client **/
@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private String nickname;
    private String imagePath;
    private LocalDate birthDate;
    private String readingLevel;
    private int untilNextLevel;
    private List<WantCreateResponse> wantToReads;

    // User -> UserInfoResponse로 변환
    public static UserInfoResponse from(User user){
        return new UserInfoResponse(
                user.getNickname(),
                user.getImagePath(),
                user.getBirthDate(),
                user.getReadingLevel().getKoreanName(),
                user.CountUntilNextLevel(),
                user.getWantToReads().stream()
                        .map(WantCreateResponse::from)
                        .toList()
        );
    }
}
