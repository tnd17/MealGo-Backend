package com.mealgo.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mealgo.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdOrderByIdDesc(Long userId);

    // admin lấy toàn bộ đơn mới nhất trước
    List<Order> findAllByOrderByIdDesc();
}