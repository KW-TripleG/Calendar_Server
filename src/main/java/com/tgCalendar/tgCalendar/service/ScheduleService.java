package com.tgCalendar.tgCalendar.service;

import com.tgCalendar.tgCalendar.dto.ScheduleDto;
import com.tgCalendar.tgCalendar.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tgCalendar.tgCalendar.repository.ScheduleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Schedule findById(int schedule_id) {
        Optional<Schedule> schedule = scheduleRepository.findById(schedule_id);

        return schedule.orElse(null);
    }

    public int updateSchedule(ScheduleDto schedule) {
        int scheduleId = scheduleRepository.save(Schedule.builder()
                        .schedule_id(schedule.getSchedule_id())
                        .id(schedule.getId())
                        .title(schedule.getTitle())
                        .content(schedule.getContent())
                        .startDate(schedule.getStartDate())
                        .endDate(schedule.getEndDate())
                        .togetherId(schedule.getTogetherId())
                .build()).getSchedule_id();

        return scheduleId;
    }
}
