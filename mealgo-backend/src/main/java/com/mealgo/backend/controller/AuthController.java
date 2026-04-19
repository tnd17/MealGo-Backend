package com.mealgo.backend.controller;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealgo.backend.dto.LoginRequest;
import com.mealgo.backend.dto.LoginResponse;
import com.mealgo.backend.dto.RegisterRequest;
import com.mealgo.backend.entity.User;
import com.mealgo.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController // bien class thanh noi tao API
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register") // API POST /api/auth/register
    public String register(@RequestBody RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return "Email already exists";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        // hash password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user); // INSERT vao MYSQL

        return "Register success";
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            return "Email not found";
        }

        // check hash
        boolean correctPassword = passwordEncoder.matches(request.getPassword(), user.get().getPassword());

        if (!correctPassword) {
            return "Wrong password";
        }

        // return user.get(); //Frontend nhan object user
        User u = user.get();

        return new LoginResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getRole());
    }
}
