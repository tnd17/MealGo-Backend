package com.mealgo.backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    private Long userId;

    private String fullName;

    private String phone;

    private String address;

    private String note;

    private String paymentMethod;

    private List<OrderItemRequest> items;
}
