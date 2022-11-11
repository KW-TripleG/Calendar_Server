package com.tgCalendar.tgCalendar.service;

import com.tgCalendar.tgCalendar.dto.UserDto;
import com.tgCalendar.tgCalendar.entity.User;
import com.tgCalendar.tgCalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String saveUser(UserDto user) {
        String userId = userRepository.save(User.builder()
                        .id(user.getId())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .email(user.getEmail())
                        .nickName(user.getNickName())
                        .build()).getId();

        return userId;
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);
    }
}
