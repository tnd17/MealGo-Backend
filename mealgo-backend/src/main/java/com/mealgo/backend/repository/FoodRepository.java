package com.mealgo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.backend.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

    // search theo tên món
    List<Food> findByNameContainingIgnoreCase(String keyword);

    // filter theo category id
    List<Food> findByCategoryId(Long categoryId);
}
