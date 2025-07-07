package com.fwaiya.princess_backend.login.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
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
    public ApiResponse<?> joinProcess(JoinRequestDto joinRequestDto) {

        String username = joinRequestDto.getUserId();
        String nickname = joinRequestDto.getNickname();
        String password = joinRequestDto.getPassword();
        LocalDate birthDate = joinRequestDto.getBirthDate();

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
        user.setBirthDate(birthDate);
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return ApiResponse.onSuccess(SuccessCode.USER_JOIN_SUCCESS, "회원가입이 완료되었습니다.");
    }

}

