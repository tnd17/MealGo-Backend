package com.mealgo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mealgo.backend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
