package com.footballiq.demo.task;

import com.footballiq.demo.repository.OtpRequestRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class OtpCleanupTask {

    private final OtpRequestRepository otpRequestRepository;

    public OtpCleanupTask(OtpRequestRepository otpRequestRepository) {
        this.otpRequestRepository = otpRequestRepository;
    }

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    @Transactional
    public void cleanupExpiredOtps() {
        otpRequestRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("Ran OTP Cleanup Task: Deleted expired OTPs.");
    }
}
