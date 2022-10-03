package com.tgCalendar.tgCalendar.service;

import com.tgCalendar.tgCalendar.dto.UserDto;
import com.tgCalendar.tgCalendar.entity.User;
import com.tgCalendar.tgCalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    public UserDto findById(String id) {
//        Optional<User> user = userRepository.findById(id);
//
//        if(user.isPresent()){
//
//
//        }
//
//    }

}
