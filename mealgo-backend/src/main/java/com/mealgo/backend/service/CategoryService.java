package com.mealgo.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealgo.backend.entity.Category;
import com.mealgo.backend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
