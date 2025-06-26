package com.fwaiya.princess_backend.login.controller;

import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
import com.fwaiya.princess_backend.login.service.JoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** *회원가입 컨트롤러 클래스 *회원 가입 기능 제공 *** @author yaaan7 @since 2025-06-26 */
@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "회원가입 로직")
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinRequestDto joinRequestDto) {

        System.out.println(joinRequestDto.getNickname());
        joinService.joinProcess(joinRequestDto);

        return "ok";
    }
}
