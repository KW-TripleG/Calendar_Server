package com.tgCalendar.tgCalendar.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String password;
    private String name;
    private String email;
}
