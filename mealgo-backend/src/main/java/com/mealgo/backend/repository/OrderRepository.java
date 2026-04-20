package com.mealgo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mealgo.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
