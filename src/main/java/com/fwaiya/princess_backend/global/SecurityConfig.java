package com.fwaiya.princess_backend.global;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf disable
        http.csrf(csrf -> csrf.disable());
        // 기존 로그인 방식 disable
        http.formLogin((auth) -> auth.disable());
        // http basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());
        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        // 로그인 없이 접근 가능
                        .requestMatchers(
                                "/login",
                                "/",
                                "/join"
                        ).permitAll()
                        // admin 페이지엔 role이 관리자일 때만 접근 가능
                        //.requestMatchers("/admin").hasRole("ADMIN")
                        // 외엔 로그인한 사용자만 접근 가능
                        .anyRequest().authenticated());
        // 세션 설정
        http
                .sessionManagement((session)-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}