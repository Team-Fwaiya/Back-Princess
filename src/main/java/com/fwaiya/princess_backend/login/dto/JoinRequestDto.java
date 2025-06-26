package com.fwaiya.princess_backend.login.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;;

@Getter
@Setter
public class JoinRequestDto {

    private String nickname;
    private String password;
    private LocalDate birthDate;
    private String imagePath;

}
