package com.mealgo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.entity.Category;
import com.mealgo.backend.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
