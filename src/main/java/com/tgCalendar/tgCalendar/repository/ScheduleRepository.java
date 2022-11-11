package com.tgCalendar.tgCalendar.repository;

import com.tgCalendar.tgCalendar.entity.Schedule;
import com.tgCalendar.tgCalendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Optional<Schedule> findById(String id);

}
