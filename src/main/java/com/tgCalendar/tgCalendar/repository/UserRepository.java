package com.tgCalendar.tgCalendar.repository;

import com.tgCalendar.tgCalendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);
    void delete(User user);
}

