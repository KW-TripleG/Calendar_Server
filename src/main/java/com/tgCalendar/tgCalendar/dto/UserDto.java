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
public class UserDto {
    private String id;
    private String password;
    private String nickName;
    private String email;

    // entity를 Dto로 변경
    public UserDto(User entity) {
        this.id = entity.getId();
        this.nickName = entity.getNickName();
        this.email = entity.getEmail();
    }
}
