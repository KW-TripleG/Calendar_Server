package com.tgCalendar.tgCalendar.v1;

import com.tgCalendar.tgCalendar.dto.ScheduleDto;
import com.tgCalendar.tgCalendar.entity.Schedule;
import com.tgCalendar.tgCalendar.security.SecurityUtil;
import com.tgCalendar.tgCalendar.service.ScheduleService;
import com.tgCalendar.tgCalendar.util.Response;
import com.tgCalendar.tgCalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public ResponseEntity<Response> scheduleList() {
        String userId = SecurityUtil.getCurrentMemberId();

        List<Schedule> scheduleList = scheduleService.findAllByUserId(userId);
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        scheduleList.forEach(schedule -> scheduleDtoList.add(new ScheduleDto(schedule)));

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(scheduleDtoList)
                .message("유저의 모든 스케줄 리스트 반환 성공")
                .build();
        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }

    @PutMapping("/schedule")
    public ResponseEntity<Response> login(@RequestBody ScheduleDto schedule) {
        String userId = SecurityUtil.getCurrentMemberId();
        int savedScheduleId = scheduleService.saveSchedule(userId, schedule);

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(savedScheduleId)
                .message("스케줄 등록 성공")
                .build();

        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }

    @PutMapping("/update-schedule")
    public ResponseEntity<Response> updateSchedule(@RequestBody ScheduleDto schedule) {
        if (scheduleService.findByScheduleId(schedule.getScheduleId()) == null) {
            Response body = Response.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("없는 스케쥴입니다")
                    .build();
            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.BAD_REQUEST);
        }

        int updateScheduleId = scheduleService.updateSchedule(schedule);

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(updateScheduleId)
                .message("Schedule 변경 성공")
                .build();
        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-schedule")
    public ResponseEntity<Response> deleteSchedule(@RequestBody ScheduleDto schedule) {
        if (scheduleService.findByScheduleId(schedule.getScheduleId()) == null) {
            Response body = Response.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("없는 스케쥴입니다")
                    .build();
            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.BAD_REQUEST);
        }

        int deletedScheduleId = scheduleService.deleteSchedule(schedule);

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(deletedScheduleId)
                .message("Schedule 삭제 완료")
                .build();

        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }
}
