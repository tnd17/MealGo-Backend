package com.mealgo.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealgo.backend.dto.LoginRequest;
import com.mealgo.backend.dto.LoginResponse;
import com.mealgo.backend.dto.RegisterRequest;
import com.mealgo.backend.entity.User;
import com.mealgo.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // xử lý đăng ký
    public String register(RegisterRequest request){

        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()){
            return "Email already exists";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // hash password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        userRepository.save(user);

        return "Register success";
    }

    // xử lý login
    public Object login(LoginRequest request){

        Optional<User> user =
                userRepository.findByEmail(request.getEmail());

        if(user.isEmpty()){
            return "Email not found";
        }

        boolean correctPassword =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.get().getPassword()
                );

        if(!correctPassword){
            return "Wrong password";
        }

        User u = user.get();

        return new LoginResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getRole()
        );
    }
}
