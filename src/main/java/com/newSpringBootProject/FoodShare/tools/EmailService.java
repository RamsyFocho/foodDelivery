package com.newSpringBootProject.FoodShare.tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail,String status) {
        System.out.println("----------Want to send email.......................");
        SimpleMailMessage message = getSimpleMailMessage(toEmail, status);
        try {
            mailSender.send(message);
            System.out.println("------------------Email sent successfully!-----------------------");
        } catch (Exception e) {
            // This is where you can check if the exception is due to exceeding the daily sending limit.
            // Gmail may return an SMTP error code like 452 or 550 when the quota is reached.
            System.err.println("Failed to send email: " + e.getMessage());
            // You can log the error and implement a fallback mechanism (e.g., queue for retry later or notify admin).
        }
    }

    private static SimpleMailMessage getSimpleMailMessage(String toEmail, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Order Confirmation - FoodShare");
        String msg="";
        if(Objects.equals(status, "PROCESSING")){
             msg = " Please send the money through this mobile money account 653565783 - Dian Zang Nsigha";
        }
        if(Objects.equals(status, "ACTIVE")){
            msg = "Thank you for your order. Your order has been placed successfully. Your order is now being processed. We will notify you once it is ready.";
        }
        message.setText(msg);
        return message;
    }
}

