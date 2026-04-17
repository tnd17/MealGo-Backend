package com.mealgo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.backend.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

}
