package com.fwaiya.princess_backend.login.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwaiya.princess_backend.domain.User;
import com.fwaiya.princess_backend.global.api.ApiResponse;
import com.fwaiya.princess_backend.global.api.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** JWT를 검증하기 위한 커스텀 필터 ** @author yaaan7 * @since 2025-06-28 */
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization=request.getHeader("Authorization");

        // Swagger 요청은 JWT 검사 생략
        String path = request.getRequestURI();
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 인증 안된 사용자면 중단
        if ( authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null : 인증이 안된 사용자입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    ApiResponse.onFailure(ErrorCode.TOKEN_NOT_FOUND, null)
            ));
            return;
        }

        String token = authorization.substring(7);

        // 기한이 지난 토큰이면 중단
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired : 기한이 지난 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    ApiResponse.onFailure(ErrorCode.TOKEN_EXPIRED, null)
            ));
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        User user = new User();
        user.setUsername(username);
        user.setPassword("temppassword");
        user.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
