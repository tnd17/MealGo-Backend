package com.mealgo.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.backend.entity.Food;
import com.mealgo.backend.repository.FoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public List<Food> getAllFoods(){
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id){
        return foodRepository.findById(id).orElse(null);
    }
}
