package com.tgCalendar.tgCalendar.service;

import com.tgCalendar.tgCalendar.dto.UserRequestDto;
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

    public String saveUser(UserRequestDto user) {
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

    public User updateUser(String currentUserId, UserRequestDto user) {
        User updatingUser = findById(currentUserId);

        if (user.getId() != null) {
            updatingUser.setId(user.getId());
        }
        if (user.getPassword() != null) {
            updatingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getEmail() != null) {
            updatingUser.setEmail(user.getEmail());
        }
        if (user.getNickName() != null) {
            updatingUser.setNickName(user.getNickName());
        }

        return userRepository.save(updatingUser);
    }
}
