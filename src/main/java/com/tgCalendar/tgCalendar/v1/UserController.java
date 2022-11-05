package com.tgCalendar.tgCalendar.v1;

import com.tgCalendar.tgCalendar.entity.User;
import com.tgCalendar.tgCalendar.security.jwt.JwtTokenProvider;
import com.tgCalendar.tgCalendar.repository.UserRepository;
import com.tgCalendar.tgCalendar.util.Response;
import com.tgCalendar.tgCalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Response> join(@RequestBody Map<String, String> user) {

        String userId = userRepository.save(User.builder()
                                                .id(user.get("id"))
                                                .password(passwordEncoder.encode(user.get("password")))
                                                .email(user.get("email"))
                                                .nickName(user.get("nickname"))
                                                .build())
                                        .getId();

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(userId)
                .message("성공")
                .build();

        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findById(user.get("id"))                                   // DB 에 애초에 없는 id 면 가입되지 않은 것
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 유저입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {             // 비밀번호 맞는지는 passwordEncoder 에서 확인
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(member.getUsername());                              // 존재하는 id 이고, 패스워드도 올바르다면 -> createToken 함수로 유저 id 정보를 담은 JWT 토큰 반환
                                                                                                // 이후 클라이언트는 이 JWT 토큰을 'X-AUTH-TOKEN' 필드에 넣어서 요청
    }
}