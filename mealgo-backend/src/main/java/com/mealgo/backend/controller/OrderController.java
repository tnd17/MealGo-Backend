package com.mealgo.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.dto.AdminOrderResponse;
import com.mealgo.backend.dto.CreateOrderRequest;
import com.mealgo.backend.dto.OrderHistoryResponse;
import com.mealgo.backend.dto.OrderResponse;
import com.mealgo.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/user/{userId}")
    public List<OrderHistoryResponse> getOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    // admin lấy tất cả đơn
    @GetMapping("/admin")
    public List<AdminOrderResponse> getAllOrders() {
        return orderService.getAllOrdersForAdmin();
    }

    // admin update status
    @PutMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    // api pay order
    @PutMapping("/{id}/pay")
    public String payOrder(
            @PathVariable Long id,
            @RequestParam boolean success) {

        return orderService.payOrder(id, success);
    }

    // admin confirm payment
    @PutMapping("/{id}/confirm-payment")
    public String confirmPayment(@PathVariable Long id) {
        return orderService.confirmPayment(id);
    }

    // guest get order
    @GetMapping("/guest-track")
    public ResponseEntity<?> trackGuestOrder(
            @RequestParam Long orderId,
            @RequestParam String email) {
        OrderHistoryResponse response = orderService.getGuestOrder(orderId, email);

        if (response == null) {
            return ResponseEntity.badRequest()
                    .body("Order not found");
        }

        return ResponseEntity.ok(response);
    }
}