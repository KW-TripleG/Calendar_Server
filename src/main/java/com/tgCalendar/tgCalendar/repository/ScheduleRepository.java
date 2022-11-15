package com.tgCalendar.tgCalendar.repository;

import com.tgCalendar.tgCalendar.entity.Schedule;
import com.tgCalendar.tgCalendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findByScheduleId(int scheduleId);

    List<Schedule> findAllByUserId_Id(@Param(value = "userId") String userId);
}