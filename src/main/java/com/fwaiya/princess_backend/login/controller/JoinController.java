package com.fwaiya.princess_backend.login.controller;

import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import com.fwaiya.princess_backend.global.api.SuccessCode;
import com.fwaiya.princess_backend.global.exception.GeneralException;
import com.fwaiya.princess_backend.login.dto.JoinRequestDto;
import com.fwaiya.princess_backend.login.dto.JoinResponseDto;
import com.fwaiya.princess_backend.login.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** *회원가입 컨트롤러 클래스 *회원 가입 기능 제공 *** @author yaaan7 @since 2025-06-26 */
@Slf4j
@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "01. 회원가입", description = "회원가입 API")
public class JoinController {
    private final JoinService joinService;

    @Operation(summary="회원가입", description = "사용자 정보를 받아 회원가입을 진행합니다.")
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(JoinRequestDto joinRequestDto) {
            JoinResponseDto response = joinService.joinProcess(joinRequestDto);
            return ResponseEntity.ok("회원 가입을 성공하였습니다");
    }
//        } catch (GeneralException e) {
//            log.error("회원가입 중 예외 발생: {}", e.getReason().getMessage());
//            throw e;
//        } catch (Exception e){
//            log.error("알 수 없는 예외 발생: {}", e.getMessage());
//            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
}