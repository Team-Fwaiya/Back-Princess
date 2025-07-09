package com.fwaiya.princess_backend.login.controller;

import com.fwaiya.princess_backend.login.dto.LoginRequestDto;
import com.fwaiya.princess_backend.login.jwt.CustomUserDetails;
import com.fwaiya.princess_backend.login.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** * JWT 토큰을 반환받는 컨트롤러 클래스 * 로그인 기능 제공 ** @author yaaan7 * @since 2025-06-29 */
@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "02. 로그인 및 토큰 발급")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/swagger")
    @Operation(summary = "Swagger 전용 로그인", description = " userId, password로 JWT 토큰을 발급받습니다.")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());

            return ResponseEntity.ok(jwt);

        } catch (UsernameNotFoundException e) {
            log.warn("Swagger 로그인 실패 - 존재하지 않는 아이디: {}", dto.getUserId());
            return ResponseEntity.ok("아이디나 비밀번호가 일치하지 않습니다");
        } catch (BadCredentialsException e) {
            log.warn("Swagger 로그인 실패 - 비밀번호 오류: {}", dto.getUserId());
            return ResponseEntity.ok("아이디나 비밀번호가 일치하지 않습니다");
        } catch (AuthenticationException e) {
            log.warn("Swagger 로그인 실패 - 기타 인증 오류: {}", e.getMessage());
            return ResponseEntity.ok("아이디나 비밀번호가 일치하지 않습니다");
        }
    }
}
