package com.footballiq.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6 digit OTP
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String toEmail, String otpCode) {
        // ALWAYS log it for dev testing so frontend/postman can use it immediately.
        System.out.println("=================================================");
        System.out.println("OTP CODE FOR " + toEmail + " IS: " + otpCode);
        System.out.println("=================================================");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@footballiq.com"); // Replace with your verified SendGrid sender email later
            message.setTo(toEmail);
            message.setSubject("Your FootballIQ Login OTP");
            message.setText("Your One-Time Password is: " + otpCode + "\n\nThis OTP is valid for 30 minutes. Do not share it with anyone.");

            mailSender.send(message);
        } catch (Exception e) {
            // We catch the exception and log it so that if SendGrid isn't configured yet,
            // the API doesn't crash and you can still test the login flow using the printed OTP above.
            System.err.println("Failed to send email. Check SMTP settings. Error: " + e.getMessage());
        }
    }
}
