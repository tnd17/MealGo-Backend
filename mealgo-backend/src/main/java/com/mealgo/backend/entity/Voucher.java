package com.mealgo.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private Double discountPercent;

    private Double minOrderValue;

    private LocalDateTime expiryDate;

    private Integer usageLimit;

    private Integer usedCount = 0;

    private Boolean active = true;
}
