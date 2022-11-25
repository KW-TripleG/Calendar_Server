package com.tgCalendar.tgCalendar.service;

import com.tgCalendar.tgCalendar.dto.ScheduleDto;
import com.tgCalendar.tgCalendar.entity.Schedule;
import com.tgCalendar.tgCalendar.entity.User;
import com.tgCalendar.tgCalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tgCalendar.tgCalendar.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public Schedule findByScheduleId(int schedule_id) {
        Optional<Schedule> schedule = scheduleRepository.findByScheduleId(schedule_id);
        return schedule.orElse(null);
    }

    public List<Schedule> findAllByUserId(String userId) {
        List<Schedule> scheduleList = scheduleRepository.findAllByUserId_Id(userId);
        return scheduleList;
    }

    public int saveSchedule(String currentUserId, ScheduleDto schedule) {
        User currentUser = userService.findById(currentUserId);

        int scheduleId = scheduleRepository.save(Schedule.builder()
                        .userId(currentUser)
                        .title(schedule.getTitle())
                        .content(schedule.getContent())
                        .startDate(schedule.getStartDate())
                        .endDate(schedule.getEndDate())
                        .duration(schedule.getDuration())
                        .build())
                .getScheduleId();

        return scheduleId;
    }

    public int updateSchedule(ScheduleDto schedule) {
        Schedule updatingSchedule = findByScheduleId(schedule.getScheduleId());

        if (schedule.getTitle() != null) {
            updatingSchedule.setTitle(schedule.getTitle());
        }
        if (schedule.getContent() != null) {
            updatingSchedule.setContent(schedule.getContent());
        }
        if (schedule.getStartDate() != null) {
            updatingSchedule.setStartDate(schedule.getStartDate());
        }
        if (schedule.getEndDate() != null) {
            updatingSchedule.setEndDate(schedule.getEndDate());
        }

        return scheduleRepository.save(updatingSchedule).getScheduleId();
    }

    public int deleteSchedule(ScheduleDto schedule) {
        Schedule deletedSchedule = findByScheduleId(schedule.getScheduleId());

        scheduleRepository.delete(deletedSchedule);

        return 0;
    }
}
