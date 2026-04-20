package com.mealgo.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.dto.LoginRequest;
import com.mealgo.backend.dto.RegisterRequest;
import com.mealgo.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
