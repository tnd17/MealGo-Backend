package com.mealgo.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOrderEmail(String toEmail, Long orderId) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("MealGo - Order Confirmation");

        message.setText(
            "Thank you for your order!\n\n" +
            "Order ID: #" + orderId + "\n" +
            "We will contact you soon."
        );

        mailSender.send(message);
    }
}
