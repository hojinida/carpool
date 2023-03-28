package com.project.carpool.user.application;

import com.project.carpool.user.domain.User;
import com.project.carpool.user.domain.repository.UserRepository;
import com.project.carpool.user.presentation.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void join(UserCreateRequest request) {
        userRepository.save(User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build());
    }
}
