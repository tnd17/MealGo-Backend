package com.mealgo.backend.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mealgo.backend.entity.Order;
import com.mealgo.backend.entity.OrderItem;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // =========================
    // Guest CARD -> gửi mail hướng dẫn thanh toán
    // =========================
    public void sendOrderEmail(String toEmail, Order order, List<OrderItem> preparedItems) {

        try {
            StringBuilder itemsHtml = new StringBuilder();

            for (OrderItem item : preparedItems) {
                itemsHtml.append("<li>")
                        .append(item.getFood().getName())
                        .append(" x")
                        .append(item.getQuantity())
                        .append("</li>");
            }

            String trackingLink =
                    "http://localhost:5173/guest-order?email="
                            + order.getEmail()
                            + "&orderId="
                            + order.getId();

            String htmlContent =
                    "<h2>🍔 MealGo Payment Instruction</h2>"
                            + "<p>Thank you for your order!</p>"

                            + "<p><b>Order ID:</b> #" + order.getId() + "</p>"

                            + "<h3>Ordered Items:</h3>"
                            + "<ul>" + itemsHtml + "</ul>"

                            + "<p><b>Total:</b> $" + order.getTotalAmount() + "</p>"

                            + "<hr/>"

                            + "<h3>Payment Instruction</h3>"

                            + "<p>Please transfer payment to the following bank account:</p>"
                            
                            + "<p>"
                            + "<b>Bank:</b> MB Bank<br>"
                            + "<b>Account Number:</b> 123456789<br>"
                            + "<b>Account Name:</b> MEALGO FOOD SERVICE<br>"
                            + "</p>"
                            
                            + "<p><b>Transfer Content:</b> MEALGO " + order.getId() + "</p>"
                            
                            + "<p>"
                            + "After completing the transfer, our admin will verify your payment "
                            + "and confirm your order via email."
                            + "</p>"

                            + "<br/>"

                            + "<p>You can track your order here:</p>"
                            + "<a href='" + trackingLink + "'>"
                            + "Track Order"
                            + "</a>"

                            + "<br/><br/>"

                            + "<p>After payment, admin will confirm your order.</p>";

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("MealGo - Payment Instructions");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // Admin confirm payment
    // =========================
    public void sendConfirmPaymentEmail(String toEmail, Long orderId) {

        try {
            String htmlContent =
                    "<h2>✅ Payment Confirmed</h2>"
                            + "<p>Your payment has been verified successfully.</p>"
                            + "<p><b>Order ID:</b> #" + orderId + "</p>"
                            + "<p>Status: CONFIRMED</p>"
                            + "<p>Thank you for ordering with MealGo ❤️</p>";

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("MealGo - Payment Confirmed");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}