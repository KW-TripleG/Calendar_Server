package com.tgCalendar.tgCalendar.dto;

import com.tgCalendar.tgCalendar.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String id;
    private String password;
    private String nickName;
    private String email;
}
