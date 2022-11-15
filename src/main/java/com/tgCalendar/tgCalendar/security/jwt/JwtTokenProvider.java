package com.tgCalendar.tgCalendar.security.jwt;

import com.tgCalendar.tgCalendar.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {                                                                         // JwtTokenProvider 의 함수들을 JwtAuthenticationFilter 에서 사용

    private String secretKey = "myprojectsecret";                                                            // token encode & decode 에 사용
    private long tokenValidTime = 10 * 60 * 1000L;                                                      // 토큰 유효시간 30분

    private final CustomUserDetailService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());                           // 객체 초기화, secretKey를 Base64로 인코딩
    }

    public String createToken(String userPk) {                                                          // JWT 토큰 생성
        Claims claims = Jwts.claims().setSubject(userPk);                                               // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)                                                                      // 정보 저장
                .setIssuedAt(now)                                                                       // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime))                                // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)                                          // 사용할 암호화 알고리즘과 secret key 지정 (signature 에 들어갈 secret값 세팅)
                .compact();
    }

    public Authentication getAuthentication(String token) {                                             // JWT 토큰에서 인증 정보 조회
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));         // getUserPk 에 token 넣어서 username 획득 (우리 코드에선 유저 id) -> userDetailsService 에서 유저 찾기
        return new UsernamePasswordAuthenticationToken(userDetails, "");                       // 인증용 객체 생성 (http 요청 발생 시 AuthenticationFilter 가 요청을 가로채서 생성)
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();     // secret key 로 token decode -> 토큰에 담겨져있던 유저 id 반환
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");                                                 // http request header 에서 'X-AUTH-TOKEN' 필드의 값 가져오기 ("X-AUTH-TOKEN" : "TOKEN값")
    }

    public boolean validateToken(String jwtToken) {                                                     // 토큰이 유효하면 true, 만료됐으면 false 반환
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
