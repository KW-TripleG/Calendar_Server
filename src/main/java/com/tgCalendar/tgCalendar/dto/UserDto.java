package com.tgCalendar.tgCalendar.dto;

import com.tgCalendar.tgCalendar.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String id;
    private String password;
    private String nickName;
    private String email;

    public UserDto(User entity) {
        this.id = entity.getId();
        this.nickName = entity.getNickName();
        this.email = entity.getEmail();
    }
}
