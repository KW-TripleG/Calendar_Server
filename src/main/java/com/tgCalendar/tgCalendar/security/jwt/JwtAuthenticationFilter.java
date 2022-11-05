package com.tgCalendar.tgCalendar.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {                                                 // JwtTokenProvider 로부터 받아온 정보를 UsernamePasswordAuthenticationFilter 로 전달

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {            // JwtTokenProvider 의 함수들을 이용해 필터링 수행
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);                             // http request header 에서 token 파싱

        if (token != null && jwtTokenProvider.validateToken(token)) {                                           // validateToken 함수로 token 유효성 검증
            Authentication authentication = jwtTokenProvider.getAuthentication(token);                          // Authentication 객체 (인증용 객체) 획득
            SecurityContextHolder.getContext().setAuthentication(authentication);                               // SecurityContext 에 Authentication 객체 저장
        }

        chain.doFilter(request, response);
    }
}
