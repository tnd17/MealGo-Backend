package com.mealgo.backend.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.backend.dto.CreateOrderRequest;
import com.mealgo.backend.dto.OrderHistoryResponse;
import com.mealgo.backend.dto.OrderItemRequest;
import com.mealgo.backend.dto.OrderResponse;
import com.mealgo.backend.entity.Food;
import com.mealgo.backend.entity.Order;
import com.mealgo.backend.entity.OrderItem;
import com.mealgo.backend.entity.User;
import com.mealgo.backend.repository.FoodRepository;
import com.mealgo.backend.repository.OrderItemRepository;
import com.mealgo.backend.repository.OrderRepository;
import com.mealgo.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        if (request == null) {
            return OrderResponse.fail("Request body is required");
        }

        if (request.getUserId() == null) {
            return OrderResponse.fail("User id is required");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            return OrderResponse.fail("Order items are required");
        }

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            return OrderResponse.fail("Full name is required");
        }

        if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
            return OrderResponse.fail("Phone is required");
        }

        if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            return OrderResponse.fail("Address is required");
        }

        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return OrderResponse.fail("User not found");
        }

        double totalAmount = 0;
        List<OrderItem> preparedOrderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            if (itemRequest.getFoodId() == null || itemRequest.getQuantity() == null || itemRequest.getQuantity() <= 0) {
                continue;
            }

            Optional<Food> foodOptional = foodRepository.findById(itemRequest.getFoodId());
            if (foodOptional.isEmpty()) {
                continue;
            }

            Food food = foodOptional.get();
            double lineTotal = food.getPrice() * itemRequest.getQuantity();
            totalAmount += lineTotal;

            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(food.getPrice());
            preparedOrderItems.add(orderItem);
        }

        if (preparedOrderItems.isEmpty() || totalAmount <= 0) {
            return OrderResponse.fail("No valid items to create order");
        }

        Order order = new Order();
        order.setUser(userOptional.get());
        order.setFullName(request.getFullName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());
        order.setStatus("PENDING");
        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        for (OrderItem preparedOrderItem : preparedOrderItems) {
            preparedOrderItem.setOrder(order);
            orderItemRepository.save(preparedOrderItem);
        }

        return OrderResponse.success("Order created successfully", order.getId());
    }

    public List<OrderHistoryResponse> getOrdersByUser(Long userId){

        List<Order> orders = orderRepository.findByUserIdOrderByIdDesc(userId);
    
        List<OrderHistoryResponse> result = new ArrayList<>();
    
        for(Order order : orders){
            result.add(
                new OrderHistoryResponse(
                    order.getId(),
                    order.getTotalAmount(),
                    order.getStatus(),
                    order.getCreatedAt().toString()
                )
            );
        }
    
        return result;
    }
}
