package com.tgCalendar.tgCalendar.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;

    private Date date;

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
