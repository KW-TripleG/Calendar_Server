package com.tgCalendar.tgCalendar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserDto {
    private String id;
    private String password;
    private String nickName;
    private String email;
}
