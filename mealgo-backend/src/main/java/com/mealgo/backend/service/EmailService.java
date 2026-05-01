package com.mealgo.backend.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mealgo.backend.entity.Order;
import com.mealgo.backend.entity.OrderItem;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    //gửi mail khi đặt đơn
    public void sendOrderEmail(String toEmail, Order order, List<OrderItem> preparedItems) {

        StringBuilder items = new StringBuilder();
    
        for (OrderItem item : preparedItems) {
            items.append("- ")
                .append(item.getFood().getName())
                .append(" x")
                .append(item.getQuantity())
                .append("\n");
        }
    
        SimpleMailMessage message = new SimpleMailMessage();
    
        message.setTo(toEmail);
        message.setSubject("MealGo - Payment Instructions");
    
        message.setText(
            "Thank you for your order!\n\n" +
    
            "Order ID: #" + order.getId() + "\n\n" +
    
            "Items:\n" + items +
    
            "\nTotal: $" + order.getTotalAmount() +
    
            "\n\n--- PAYMENT INSTRUCTION ---\n" +
            "Please scan QR or transfer manually (demo).\n" +
            "Content: MEALGO " + order.getId() + "\n\n" +
    
            "After payment, admin will confirm your order.\n\n" +
            "Thank you!"
        );
    
        mailSender.send(message);
    }

    //gửi mail khi admin confirm thanh toán
    public void sendConfirmPaymentEmail(String toEmail, Long orderId) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("MealGo - Payment Confirmed");

        message.setText(
            "Your order has been confirmed!\n\n" +
            "Order ID: #" + orderId + "\n" +
            "Status: CONFIRMED\n\n" +
            "Thank you for ordering with MealGo ❤️"
        );

        mailSender.send(message);
    }
}