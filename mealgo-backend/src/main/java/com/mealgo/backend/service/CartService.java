package com.mealgo.backend.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealgo.backend.entity.*;
import com.mealgo.backend.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public Cart getOrCreateCart(Long userId) {

        return cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                Cart cart = new Cart();
                cart.setUser(userRepository.findById(userId).get());
                return cartRepository.save(cart);
            });
    }

    public List<CartItem> getCart(Long userId){
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.findByCartId(cart.getId());
    }

    public void addItem(Long userId, Long foodId){

        Cart cart = getOrCreateCart(userId);

        Optional<CartItem> opt =
            cartItemRepository.findByCartIdAndFoodId(cart.getId(), foodId);

        if(opt.isPresent()){
            CartItem item = opt.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        }else{
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setFood(foodRepository.findById(foodId).get());
            item.setQuantity(1);
            cartItemRepository.save(item);
        }
    }

    public void removeItem(Long userId, Long foodId){

        Cart cart = getOrCreateCart(userId);

        Optional<CartItem> opt =
            cartItemRepository.findByCartIdAndFoodId(cart.getId(), foodId);

        if(opt.isPresent()){

            CartItem item = opt.get();

            if(item.getQuantity() <= 1){
                cartItemRepository.delete(item);
            }else{
                item.setQuantity(item.getQuantity() - 1);
                cartItemRepository.save(item);
            }
        }
    }

    @Transactional
    public void clearCart(Long userId){

        Cart cart = getOrCreateCart(userId);

        cartItemRepository.deleteByCartId(cart.getId());
    }
}