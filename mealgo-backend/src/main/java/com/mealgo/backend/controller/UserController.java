package com.mealgo.backend.controller;
import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.entity.User;
import com.mealgo.backend.repository.UserRepository;

@RestController //trả JSON cho frontend
@RequestMapping("/api/users") //đường dẫn gốc API
@CrossOrigin //cho phép frontend truy cập API từ máy khác
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping //API POST tạo user mới
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
