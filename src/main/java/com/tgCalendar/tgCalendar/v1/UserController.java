package com.tgCalendar.tgCalendar.v1;

import com.tgCalendar.tgCalendar.dto.UserDto;
import com.tgCalendar.tgCalendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

//    @GetMapping("/me")
//    public UserDto getMyUserInfo() {
//        UserDto me = new UserDto();
//
//
//
//        return me;
//    }


}
