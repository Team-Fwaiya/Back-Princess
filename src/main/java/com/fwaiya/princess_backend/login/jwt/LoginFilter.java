package com.fwaiya.princess_backend.login.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwaiya.princess_backend.login.dto.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        // SecurityConfig에서 이미 /login 경로가 설정되어 있으므로 여기서는 설정하지 않음
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Content-Type 확인
            String contentType = request.getContentType();
            log.info("Content-Type: {}", contentType);

            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            String username = loginRequest.getUserId();
            String password = loginRequest.getPassword();

            log.info("로그인 시도 - 사용자: {}", username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            log.error("로그인 요청 파싱 실패", e);
            throw new RuntimeException("로그인 요청 파싱 실패", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.createJwt(username, role);

        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Authorization", "Bearer " + token);

        // 응답 본문에도 토큰 포함
        String jsonResponse = String.format("{\"message\":\"로그인 성공\",\"token\":\"Bearer %s\"}", token);
        response.getWriter().write(jsonResponse);

        log.info("로그인 성공 - 사용자: {}", username);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String message;
        if (failed instanceof UsernameNotFoundException) {
            message = "존재하지 않는 사용자입니다.";
            log.warn("로그인 실패: 존재하지 않는 사용자 - {}", failed.getMessage());
        } else if (failed instanceof BadCredentialsException) {
            message = "아이디 또는 비밀번호가 일치하지 않습니다.";
            log.warn("로그인 실패: 비밀번호 오류 - {}", failed.getMessage());
        } else {
            message = "로그인에 실패했습니다.";
            log.warn("로그인 실패: 기타 인증 실패 - {}", failed.getMessage());
        }

        String jsonResponse = String.format("{\"error\":\"%s\"}", message);
        response.getWriter().write(jsonResponse);
    }
}