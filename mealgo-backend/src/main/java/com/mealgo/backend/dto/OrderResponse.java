package com.mealgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private boolean success;
    private String message;
    private Long orderId;

    public static OrderResponse success(String message, Long orderId) {
        return new OrderResponse(true, message, orderId);
    }

    public static OrderResponse fail(String message) {
        return new OrderResponse(false, message, null);
    }
}
