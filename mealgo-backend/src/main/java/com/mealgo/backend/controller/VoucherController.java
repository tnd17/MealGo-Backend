package com.mealgo.backend.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.entity.Voucher;
import com.mealgo.backend.repository.VoucherRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
@CrossOrigin
public class VoucherController {

    private final VoucherRepository voucherRepository;

    @PostMapping("/apply")
    public Object applyVoucher(
            @RequestBody Map<String, Object> request
    ) {
        String code = request.get("code").toString();
        Double totalAmount =
                Double.valueOf(request.get("totalAmount").toString());

        Optional<Voucher> optional =
                voucherRepository.findByCode(code);

        if (optional.isEmpty()) {
            throw new RuntimeException("Voucher not found");
        }

        Voucher voucher = optional.get();

        if (!voucher.getActive()) {
            throw new RuntimeException("Voucher inactive");
        }

        if (voucher.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Voucher expired");
        }

        if (voucher.getUsedCount() >= voucher.getUsageLimit()) {
            throw new RuntimeException("Voucher out of stock");
        }

        if (totalAmount < voucher.getMinOrderValue()) {
            throw new RuntimeException(
                    "Minimum order is $" + voucher.getMinOrderValue()
            );
        }

        double discount =
                totalAmount * voucher.getDiscountPercent() / 100;

        return Map.of(
                "discountAmount", discount
        );
    }
}
