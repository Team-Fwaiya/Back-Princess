package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.domain.WantToRead;
import com.fwaiya.princess_backend.dto.request.WantCreateRequest;
import com.fwaiya.princess_backend.dto.response.UserInfoResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.constant.ProfileImageConstants;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.repository.UserRepository;
import com.fwaiya.princess_backend.repository.WantToReadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



/** *사용자 정보 관리하는 서비스 클래스 * 일단,, 사용자 닉네임 반환, 삭제 기능 제공**@author yaaan7 *@since 2025-06-29*/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WantToReadRepository wantToReadRepository;

    // username 으로 사용자가 존재하는지 확인
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }

    public void existByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new GeneralException(ErrorCode.USER_NOT_FOUND);
        }
    }


    /** userId로 사용자 삭제  **/
    @Transactional
    public void withdraw(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
    }

    /** userId로 사용자 정보 조회 **/
    @Transactional
    public UserInfoResponse getUserInfo(String username) {
        User user = findByUsername(username);
        return UserInfoResponse.from(user);
    }

    /** 읽고 싶은 책 등록 **/
    @Transactional
    public void createWant(WantCreateRequest request, User user){

        WantToRead wantToRead = new WantToRead();
        wantToRead.setBookTitle(request.getBookTitle());
        wantToRead.setAuthor(request.getAuthor());
        wantToRead.setGenre(request.getGenre());

        user.addWantToRead(wantToRead);

        userRepository.save(user); //wantToRead도 자동으로 저장
    }

    /** 읽고 싶은 책 삭제 **/
    @Transactional
    public void deleteWant(Long wantId, User user){
        // 사용자 소유의 wantId가 맞는지 확인
        WantToRead wantToRead = wantToReadRepository.findByIdAndUserId(wantId, user.getId())
                .orElseThrow(() -> new GeneralException(ErrorCode.WANT_BOOK_NOT_FOUND_OR_UNAUTHORIZED));
        wantToReadRepository.delete(wantToRead);
    }

    /** 프로필 등록**/
    @Transactional
    public void updateProfile(String imagePath, User user) {
        user.updateImagePath(imagePath);
    }
}
