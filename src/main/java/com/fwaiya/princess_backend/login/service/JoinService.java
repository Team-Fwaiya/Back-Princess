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

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ApiResponse<?> joinProcess(JoinRequestDto joinRequestDto) {

        String userId = joinRequestDto.getUserId();
        String nickname = joinRequestDto.getNickname();
        String password = joinRequestDto.getPassword();
        String imagePath = joinRequestDto.getImagePath();
        LocalDate birthDate = joinRequestDto.getBirthDate();

        Boolean isExist1 = userRepository.existsByUserId(userId);
        if (isExist1) {
            throw new GeneralException(ErrorCode.ALREADY_USED_USERID);
        }

        Boolean isExist2 = userRepository.existsByNickname(nickname);
        if (isExist2) {
            throw new GeneralException(ErrorCode.ALREADY_USED_NICKNAME);
        }

        User user = new User();

        user.setNickname(nickname);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setImagePath(imagePath);
        user.setBirthDate(birthDate);

        userRepository.save(user);

        return ApiResponse.onSuccess(SuccessCode.USER_JOIN_SUCCESS, "회원가입이 완료되었습니다.");
    }

}

