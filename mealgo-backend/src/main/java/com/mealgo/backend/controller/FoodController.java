package com.mealgo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.entity.Food;
import com.mealgo.backend.service.FoodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@CrossOrigin
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public List<Food> getAllFoods(){
        return foodService.getAllFoods();
    }

    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable Long id){
        return foodService.getFoodById(id);
    }
}
