package com.fwaiya.princess_backend.login.jwt;

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
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        String username = request.getParameter("userId");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authentication){
        CustomUserDetails customuserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customuserDetails.getUsername();

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.createJwt (username, role);

        response.addHeader("Authorization", "Bearer " + token);

        log.info("로그인 성공 : {}", username);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        /*ApiResponse<?> result;
        result = ApiResponse.onFailure(ErrorCode.ID_PASSWORD_MISMATCH, null);


        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));

        // 예외 종류에 따라 로그는 다르게 출력
        if (failed instanceof UsernameNotFoundException) {
            log.info("로그인 실패: 존재하지 않는 아이디 - {}", failed.getMessage());
        } else if (failed instanceof BadCredentialsException) {
            log.info("로그인 실패: 비밀번호 오류 - {}", failed.getMessage());
        } else {
            log.info("로그인 실패: 기타 인증 실패 - {}", failed.getMessage());
        }*/

        String message;

        if (failed instanceof UsernameNotFoundException) {
            message = "존재하지 않는 사용자입니다.";
        } else if (failed instanceof BadCredentialsException) {
            message = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else {
            message = "로그인에 실패했습니다.";
        }

        response.getWriter().write(message);
        log.warn("로그인 실패: {}", message);

    }

}
