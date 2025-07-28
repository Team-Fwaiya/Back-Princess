package com.fwaiya.princess_backend.login.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
import com.fwaiya.princess_backend.login.dto.JoinResponseDto;
import com.fwaiya.princess_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/** *회원가입을 진행하는 서비스 클래스 * 사용자 등록 기능 제공 **@author yaaan7 *@since 2025-06-26*/
@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ApiResponse<JoinResponseDto> joinProcess(JoinRequestDto joinRequestDto) {

        String username = joinRequestDto.getUserId();
        String nickname = joinRequestDto.getNickname();
        String password = joinRequestDto.getPassword();
        String address = joinRequestDto.getAddress();
        //LocalDate birthDate = joinRequestDto.getBirthDate();

        Boolean isExist1 = userRepository.existsByUsername(username);
        if (isExist1) {
            return ApiResponse.onSuccess(SuccessCode.ALREADY_USED_USERID, null);
        }

        Boolean isExist2 = userRepository.existsByNickname(nickname);
        if (isExist2) {
            return ApiResponse.onSuccess(SuccessCode.ALREADY_USED_NICKNAME, null);
        }

        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setAddress(address);
        //user.setBirthDate(birthDate);
        String role = username.equals("1234@naver.com") ? "ROLE_ADMIN" : "ROLE_USER";
        user.setRole(role);

        userRepository.save(user);

        return ApiResponse.onSuccess(SuccessCode.USER_JOIN_SUCCESS, JoinResponseDto.from(user));
    }

}

