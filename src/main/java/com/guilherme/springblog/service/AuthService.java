package com.guilherme.springblog.service;

import com.guilherme.springblog.dto.RegisterRequest;
import com.guilherme.springblog.model.User;
import com.guilherme.springblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public void signup(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build();
        userRepository.save(user);
    }
}
