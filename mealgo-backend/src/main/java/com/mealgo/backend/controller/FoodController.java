package com.mealgo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.entity.Food;
import com.mealgo.backend.repository.FoodRepository;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin
public class FoodController {
    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }

    @GetMapping //API GET lấy tất cả foods
    public List<Food> getAllFoods(){
        return foodRepository.findAll();
    }

    @GetMapping("/{id}") //API GET lấy food theo id
    public Food getFoodById(@PathVariable Long id){
        return foodRepository.findById(id).orElse(null);
    }
}
