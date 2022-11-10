package com.tgCalendar.tgCalendar.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends GenericFilterBean {                                                 // JwtTokenProvider 로부터 받아온 정보를 UsernamePasswordAuthenticationFilter 로 전달
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {            // JwtTokenProvider 의 함수들을 이용해 필터링 수행
//        HttpServletRequest httpServletRequest1 = (HttpServletRequest) request;
//        StringBuffer requestURI1 = httpServletRequest1.getRequestURL();
//        System.out.println("request URI 1 : " + requestURI1);
//
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);                             // http request header 에서 token 파싱
//
//        HttpServletRequest httpServletRequest2 = (HttpServletRequest) request;
//        StringBuffer requestURI2 = httpServletRequest2.getRequestURL();
//        System.out.println("request URI 2 : " + requestURI2);
//
//
//        System.out.println("--------------------------------- doFilter token : " + token);
//
//        System.out.println("--------------------------------- validateToken : " + jwtTokenProvider.validateToken(token));
//
//        if (token != null && jwtTokenProvider.validateToken(token)) {                                           // validateToken 함수로 token 유효성 검증
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);                          // Authentication 객체 (인증용 객체) 획득
//            authentication.setAuthenticated(true);
//            SecurityContextHolder.getContext().setAuthentication(authentication);                               // SecurityContext 에 Authentication 객체 저장
//
//            System.out.println("Security Context 에 인증 정보 저장 성공 ");
//            System.out.println("authentication.getName : " + authentication.getName());
//
//
//            HttpServletRequest httpServletRequest3 = (HttpServletRequest) request;
//            StringBuffer requestURI3 = httpServletRequest3.getRequestURL();
//            System.out.println("request URI 3 : " + requestURI3);
//        }
//
//        chain.doFilter(request, response);
//
//        HttpServletRequest httpServletRequest4 = (HttpServletRequest) request;
//        StringBuffer requestURI4 = httpServletRequest4.getRequestURL();
//        System.out.println("request URI 4 : " + requestURI4);
//
//    }
//}


// 코드 참고
@RequiredArgsConstructor
//@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

            if (token != null && jwtTokenProvider.validateToken(token)) {                                           // validateToken 함수로 token 유효성 검증
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);                          // Authentication 객체 (인증용 객체) 획득
//            authentication.setAuthenticated(true);
//            SecurityContextHolder.getContext().setAuthentication(authentication);                               // SecurityContext 에 Authentication 객체 저장
//
//            System.out.println("Security Context 에 인증 정보 저장 성공 ");
//            System.out.println("authentication.getName : " + authentication.getName());
//
//
//            HttpServletRequest httpServletRequest3 = (HttpServletRequest) request;
//            StringBuffer requestURI3 = httpServletRequest3.getRequestURL();
//            System.out.println("request URI 3 : " + requestURI3);

                String userId = jwtTokenProvider.getUserPk(token);

                AbstractAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userId,null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);



        }

        } catch (Exception e) {
            System.out.println("@@@@@@@@@@@@@@@@@@@ doFilterInternal exception");
        }

        filterChain.doFilter(request,response);
    }


}

