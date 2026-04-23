package com.mealgo.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderHistoryResponse {

    private Long id;
    private Double totalAmount;
    private String status;
    private String createdAt;

    private String paymentMethod;
    private String paymentStatus;

    private List<String> items;
}