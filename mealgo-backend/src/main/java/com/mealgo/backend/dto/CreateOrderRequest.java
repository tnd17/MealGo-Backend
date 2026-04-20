package com.mealgo.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long userId;
    private String fullName;
    private String phone;
    private String address;
    private String note;
    private List<OrderItemRequest> items;
}
