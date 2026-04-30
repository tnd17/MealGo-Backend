package com.mealgo.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.backend.dto.AdminOrderResponse;
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

    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        if (request == null) {
            return OrderResponse.fail("Request body is required");
        }

        boolean isGuest = request.getUserId() == null;

        // guest bắt buộc có email
        if (isGuest && (request.getEmail() == null || request.getEmail().trim().isEmpty())) {
            return OrderResponse.fail("Email is required for guest");
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

        User user = null;

        // nếu là customer thì tìm user
        if (!isGuest) {
            Optional<User> userOptional = userRepository.findById(request.getUserId());

            if (userOptional.isEmpty()) {
                return OrderResponse.fail("User not found");
            }

            user = userOptional.get();
        }

        double totalAmount = 0;
        List<OrderItem> preparedItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            if (itemRequest.getFoodId() == null
                    || itemRequest.getQuantity() == null
                    || itemRequest.getQuantity() <= 0) {
                continue;
            }

            Optional<Food> foodOptional = foodRepository.findById(itemRequest.getFoodId());

            if (foodOptional.isEmpty()) {
                continue;
            }

            Food food = foodOptional.get();

            totalAmount += food.getPrice() * itemRequest.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(food.getPrice());

            preparedItems.add(orderItem);
        }

        if (preparedItems.isEmpty()) {
            return OrderResponse.fail("No valid items");
        }

        // tạo order
        Order order = new Order();

        order.setUser(user); // guest = null

        order.setEmail(
                isGuest
                        ? request.getEmail()
                        : user.getEmail());

        order.setAccountType(
                isGuest
                        ? "GUEST"
                        : "CUSTOMER");

        order.setFullName(request.getFullName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());

        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");

        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("UNPAID");

        order = orderRepository.save(order);

        // save order items
        for (OrderItem item : preparedItems) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        // gửi email xác nhận
        emailService.sendOrderEmail(order.getEmail(), order.getId());

        return OrderResponse.success(
                "Order created successfully",
                order.getId());
    }

    public List<OrderHistoryResponse> getOrdersByUser(Long userId) {

        List<Order> orders = orderRepository.findByUserIdOrderByIdDesc(userId);

        List<OrderHistoryResponse> result = new ArrayList<>();

        for (Order order : orders) {

            List<String> itemNames = new ArrayList<>();

            for (OrderItem item : order.getOrderItems()) {
                itemNames.add(
                        item.getFood().getName()
                                + " x"
                                + item.getQuantity());
            }

            result.add(
                    new OrderHistoryResponse(
                            order.getId(),
                            order.getTotalAmount(),
                            order.getStatus(),
                            order.getCreatedAt().toString(),
                            order.getPaymentMethod(),
                            order.getPaymentStatus(),
                            itemNames));
        }

        return result;
    }

    public List<AdminOrderResponse> getAllOrdersForAdmin() {

        List<Order> orders = orderRepository.findAllByOrderByIdDesc();

        List<AdminOrderResponse> result = new ArrayList<>();

        for (Order order : orders) {
            result.add(
                    new AdminOrderResponse(
                            order.getId(),
                            order.getFullName(),
                            order.getTotalAmount(),
                            order.getStatus(),
                            order.getCreatedAt().toString(),
                            order.getAccountType(),
                            order.getPaymentMethod(),
                            order.getPaymentStatus()));
        }

        return result;
    }

    @Transactional
    public String updateOrderStatus(Long orderId, String status) {

        Optional<Order> optional = orderRepository.findById(orderId);

        if (optional.isEmpty()) {
            return "Order not found";
        }

        Order order = optional.get();
        order.setStatus(status);

        orderRepository.save(order);

        return "Updated successfully";
    }

    @Transactional
    public String payOrder(Long orderId, boolean success) {

        Optional<Order> optional = orderRepository.findById(orderId);

        if (optional.isEmpty()) {
            return "Order not found";
        }

        Order order = optional.get();

        if (success) {
            order.setPaymentStatus("PAID");
            order.setStatus("CONFIRMED");
            order.setPaidAt(LocalDateTime.now());
        } else {
            order.setPaymentStatus("FAILED");
        }

        orderRepository.save(order);

        return "Updated";
    }
}