package com.mealgo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mealgo.backend.entity.CartItem;
import com.mealgo.backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId){
        return cartService.getCart(userId);
    }

    @PostMapping("/{userId}/add/{foodId}")
    public void addItem(@PathVariable Long userId,
                        @PathVariable Long foodId){
        cartService.addItem(userId, foodId);
    }

    @PostMapping("/{userId}/remove/{foodId}")
    public void removeItem(@PathVariable Long userId,
                           @PathVariable Long foodId){
        cartService.removeItem(userId, foodId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
    }
}