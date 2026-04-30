package com.mealgo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminOrderResponse {

    private Long id;
    private String customerName;
    private Double totalAmount;
    private String status;
    private String createdAt;
    private String accountType;
    private String paymentMethod;
    private String paymentStatus;
}
