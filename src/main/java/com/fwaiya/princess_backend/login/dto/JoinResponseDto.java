package com.fwaiya.princess_backend.login.dto;

import com.fwaiya.princess_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinResponseDto {
    private String username;
    private String nickname;

    public static JoinResponseDto from(User user) {
        return new JoinResponseDto(user.getUsername(), user.getNickname());
    }
}
