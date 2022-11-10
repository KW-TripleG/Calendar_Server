package com.tgCalendar.tgCalendar.v1;

import com.sun.net.httpserver.HttpServer;
import com.tgCalendar.tgCalendar.dto.UserDto;
import com.tgCalendar.tgCalendar.entity.User;
import com.tgCalendar.tgCalendar.security.jwt.JwtTokenProvider;
import com.tgCalendar.tgCalendar.repository.UserRepository;
import com.tgCalendar.tgCalendar.service.UserService;
import com.tgCalendar.tgCalendar.util.Response;
import com.tgCalendar.tgCalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Response> join(@RequestBody UserDto user) {
        if (userService.findById(user.getId()) != null) {
            Response body = Response.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("이미 존재하는 유저입니다.")
                    .build();
            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.BAD_REQUEST);
        }

        String savedUserId = userService.saveUser(user);

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(savedUserId)
                .message("회원가입 성공")
                .build();
        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody Map<String, String> user) {
        User member = userService.findById(user.get("id"));

        if (member == null) {                                                                     // DB 에 애초에 없는 id 면 가입되지 않은 것
            Response body = Response.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("가입되지 않은 유저입니다.")
                    .build();
            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {                 // 비밀번호 맞는지는 passwordEncoder 에서 확인
            Response body = Response.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("잘못된 비밀번호입니다.")
                    .build();
            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.BAD_REQUEST);
        }

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(jwtTokenProvider.createToken(member.getUsername()))
                .message("로그인 성공")
                .build();
        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);              // 존재하는 id 이고, 패스워드도 올바르다면 -> createToken 함수로 유저 id 정보를 담은 JWT 토큰 반환
                                                                                                    // 이후 클라이언트는 이 JWT 토큰을 'X-AUTH-TOKEN' 필드에 넣어서 요청

    }

    // 내 정보 조회 - security 필터 없이
//    @GetMapping("/user/me")
//    public ResponseEntity<Response> getMyInfo(@RequestHeader("X-AUTH-TOKEN") String token) {
//        if (token == null || jwtTokenProvider.validateToken(token) == false) {
//            Response body = Response.builder()
//                    .status(StatusEnum.UNAUTHORIZED)
//                    .message("권한이 없는 유저입니다.")
//                    .build();
//            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.UNAUTHORIZED);
//        }
//
//        String userId = jwtTokenProvider.getUserPk(token);
//
//        Response body = Response.builder()
//                .status(StatusEnum.OK)
//                .data(new UserDto(userService.findById(userId)))
//                .message("내 정보 조회 성공")
//                .build();
//        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
//    }

    //내 정보 조회
    @GetMapping("/user/me")
    public boolean getInfo() {



        return true;
    }


}