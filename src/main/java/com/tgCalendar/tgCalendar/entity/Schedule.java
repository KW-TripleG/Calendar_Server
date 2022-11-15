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
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    private int id;

    private String title;
    private String content;

    private Date startDate;
    private Date endDate;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "together_id", referencedColumnName = "id")
    private User togetherId;
}
