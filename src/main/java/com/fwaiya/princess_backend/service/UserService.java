package com.fwaiya.princess_backend.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.domain.WantToRead;
import com.fwaiya.princess_backend.dto.request.WantCreateRequest;
import com.fwaiya.princess_backend.dto.response.UserInfoResponse;
import com.fwaiya.princess_backend.dto.response.WantCreateResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.repository.UserRepository;
import com.fwaiya.princess_backend.repository.WantToReadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


/** *사용자 정보 관리하는 서비스 클래스 * 일단,, 사용자 닉네임 반환, 삭제 기능 제공**@author yaaan7 *@since 2025-06-29*/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WantToReadRepository wantToReadRepository;

    /**
     * userId로 사용자 정보 조회 ** @param userId(=username) *@return UserInfoResponse
     */
    @Transactional
    public UserInfoResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.from(user);
    }

    /**
     * userId로 사용자 삭제 ** @param userId
     */
    @Transactional
    public void withdraw(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    /** 읽고 싶은 책 등록 **/
    @Transactional
    public WantCreateResponse createWant(WantCreateRequest request, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        WantToRead wantToRead = new WantToRead();
        wantToRead.setUser(user);
        wantToRead.setBookTitle(request.getBookTitle());
        wantToRead.setAuthor(request.getAuthor());
        wantToRead.setGenre(request.getGenre());
        wantToRead.setMemo(request.getMemo());

        wantToReadRepository.save(wantToRead);

        user.getWantToReads().add(wantToRead);
        userRepository.save(user);

        // null 일 경우 처리?
        return WantCreateResponse.from(wantToRead);
    }

    /** 읽고 싶은 책 삭제 **/
    @Transactional
    public void deleteWant(Long wantId, String username){
        // 사용자 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        // 사용자 소유의 wantId가 맞는지 확인
        WantToRead wantToRead = wantToReadRepository.findByIdAndUserId(wantId, user.getId())
                // 코드 수정하기
                .orElseThrow(()-> new GeneralException(ErrorCode.WANT_NOT_FOUND));


        wantToReadRepository.delete(wantToRead);
    }


    private static final Set<String> fixedProfileImages = Set.of(
            "https://s3.bucket.com/profile/default1.png",
            "https://s3.bucket.com/profile/default2.png",
            "https://s3.bucket.com/profile/default3.png"
                );

    /** 프로필 수정 기능**/
    @Transactional
    public void updateProfile(String username, String imagePath) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        // imagePath가 고정 이미지 리스트에 포함되는지 검증
        if (!fixedProfileImages.contains(imagePath)) {
            throw new GeneralException(ErrorCode.INVALID_PROFILE_IMAGE);
        }

        user.updateImagePath(imagePath);
    }
}
