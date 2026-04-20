package com.mealgo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.backend.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
