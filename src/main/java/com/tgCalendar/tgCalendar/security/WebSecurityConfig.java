package com.tgCalendar.tgCalendar.security;

import com.tgCalendar.tgCalendar.security.jwt.JwtAuthenticationFilter;
import com.tgCalendar.tgCalendar.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();                      // 암호화에 필요한 PasswordEncoder Bean 등록
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();                                               // authenticationManager Bean 등록
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()                                                          // rest api 만을 고려하여 기본 설정은 해제
                .csrf().disable()                                                               // csrf 보안 토큰 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // 토큰 기반 인증이므로 세션 사용 X
                .and()
                .authorizeRequests()                                                            // 요청에 대한 사용권한 체크
                .anyRequest().permitAll()                                                       // role 에 따른 url 접근 권한 제한 없으니 permitAll
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);                            // jwtTokenProvider 로 JwtAuthenticationFilter 생성
                                                                                                // JwtAuthenticationFilter -> UsernamePasswordAuthenticationFilter 순으로 통과
    }
}