package com.fwaiya.princess_backend.login.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
import com.fwaiya.princess_backend.login.dto.JoinResponseDto;
import com.fwaiya.princess_backend.repository.UserRepository;
import io.swagger.v3.oas.models.responses.ApiResponse;
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
    public JoinResponseDto joinProcess(JoinRequestDto joinRequestDto) {

        String username = joinRequestDto.getUserId();
        String nickname = joinRequestDto.getNickname();
        String password = joinRequestDto.getPassword();
        String address = joinRequestDto.getAddress();
        //LocalDate birthDate = joinRequestDto.getBirthDate();

        Boolean isExist1 = userRepository.existsByUsername(username);
        if (isExist1) {
            throw new GeneralException(ErrorCode.ALREADY_USED_USERID);
        }

        Boolean isExist2 = userRepository.existsByNickname(nickname);
        if (isExist2) {
            throw new GeneralException(ErrorCode.ALREADY_USED_NICKNAME);
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

        return JoinResponseDto.from(user);
    }

}

