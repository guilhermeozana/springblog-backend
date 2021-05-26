package com.guilherme.springblog.controller;

import com.guilherme.springblog.requests.LoginRequest;
import com.guilherme.springblog.requests.RegisterRequest;
import com.guilherme.springblog.service.AuthService;
import com.guilherme.springblog.service.AuthenticationResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600L) 
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
