package com.fwaiya.princess_backend.login.service;

import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
import com.fwaiya.princess_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinRequestDto joinRequestDto) {

        String nickname = joinRequestDto.getNickname();
        String password = joinRequestDto.getPassword();
        String imagePath = joinRequestDto.getImagePath();
        LocalDate birthDate = joinRequestDto.getBirthDate();

        Boolean isExist = userRepository.existsByNickname(nickname);

        if (isExist) {

            return;
        }

        User user = new User();

        user.setNickname(nickname);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setImagePath(imagePath);
        user.setBirthDate(birthDate);

        userRepository.save(user);
    }

}

