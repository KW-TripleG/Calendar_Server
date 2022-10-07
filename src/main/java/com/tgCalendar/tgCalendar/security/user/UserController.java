package com.tgCalendar.tgCalendar.security.user;

import com.tgCalendar.tgCalendar.entity.User;
import com.tgCalendar.tgCalendar.jwt.JwtTokenProvider;
import com.tgCalendar.tgCalendar.repository.UserRepository;
import com.tgCalendar.tgCalendar.util.Message;
import com.tgCalendar.tgCalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
//    @PostMapping("/join")
//    public String join(@RequestBody Map<String, String> user) {
//        return userRepository.save(User.builder()
//                .id(user.get("id"))
//                .password(passwordEncoder.encode(user.get("password")))
//                .email(user.get("email"))
//                .nickName(user.get("nickname"))
//                .build()).getId();
//    }

    @PostMapping("/join")
    public ResponseEntity<Message> join(@RequestBody Map<String, String> user) {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = userRepository.save(User.builder()
                .id(user.get("id"))
                .password(passwordEncoder.encode(user.get("password")))
                .email(user.get("email"))
                .nickName(user.get("nickname"))
                .build()).getId();

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공");
        message.setData(userId);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }


    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findById(user.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername());
    }
}