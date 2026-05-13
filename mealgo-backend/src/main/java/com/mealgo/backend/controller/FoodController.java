package com.mealgo.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mealgo.backend.entity.Food;
import com.mealgo.backend.service.FoodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@CrossOrigin
public class FoodController {

    private final FoodService foodService;

    // GET ALL
    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    // GET ONE
    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    // SEARCH
    @GetMapping("/search")
    public List<Food> searchFood(
            @RequestParam String keyword
    ) {
        return foodService.searchFood(keyword);
    }

    // FILTER CATEGORY
    @GetMapping("/category/{categoryId}")
    public List<Food> filterCategory(
            @PathVariable Long categoryId
    ) {
        return foodService.filterByCategory(categoryId);
    }

    // CREATE
    @PostMapping
    public Food createFood(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        return foodService.createFood(
                name,
                description,
                price,
                categoryId,
                image
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public Food updateFood(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        return foodService.updateFood(
                id,
                name,
                description,
                price,
                categoryId,
                image
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return "Deleted successfully";
    }
}
