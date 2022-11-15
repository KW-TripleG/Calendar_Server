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
public class UserResponseDto {
    private String id;
    private String nickName;
    private String email;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.nickName = entity.getNickName();
        this.email = entity.getEmail();
    }
}
