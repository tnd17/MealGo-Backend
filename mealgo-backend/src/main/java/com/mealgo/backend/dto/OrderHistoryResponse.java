package com.mealgo.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {

    private Long id;
    private Double totalAmount;
    private String status;
    private String createdAt;
}
