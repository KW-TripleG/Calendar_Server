package com.tgCalendar.tgCalendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tgCalendar.tgCalendar.entity.Schedule;
import com.tgCalendar.tgCalendar.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private int scheduleId;
    private int id;

    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;

    private String duration;

    private String userId;
    private String togetherId;

    public ScheduleDto(Schedule entity) {
        this.scheduleId = entity.getScheduleId();
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.duration = entity.getDuration();
        this.userId = entity.getUserId().getId();

        if(entity.getTogetherId() != null) {
            this.togetherId = entity.getTogetherId().getId();
        }
    }
}