package com.tgCalendar.tgCalendar.v1;

import com.tgCalendar.tgCalendar.dto.ScheduleDto;
import com.tgCalendar.tgCalendar.service.ScheduleService;
import com.tgCalendar.tgCalendar.util.Response;
import com.tgCalendar.tgCalendar.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PutMapping("/update")
    public ResponseEntity<Response> updateSchedule(@RequestBody ScheduleDto schedule) {
        if (scheduleService.findById(schedule.getSchedule_id()) == null) {
            Response body = Response.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("없는 스케쥴입니다")
                    .build();
            return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.BAD_REQUEST);
        }

        int updateSchedule = scheduleService.updateSchedule(schedule);

        Response body = Response.builder()
                .status(StatusEnum.OK)
                .data(updateSchedule)
                .message("Schedule 변경 성공")
                .build();


        return new ResponseEntity<>(body, Response.getDefaultHeader(), HttpStatus.OK);
    }

}
