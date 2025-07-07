package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.dto.response.UserInfoResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/** *사용자 정보 관리하는 서비스 클래스 * 일단,, 사용자 닉네임 반환, 삭제 기능 제공**@author yaaan7 *@since 2025-06-29*/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /** userId로 사용자 정보 조회 ** @param userId(=username) *@return UserInfoResponse */
    @Transactional
    public UserInfoResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.from(user);
    }

    /** userId로 사용자 삭제 ** @param userId */
    @Transactional
    public void withdraw(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
