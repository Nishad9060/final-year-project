package com.footballiq.demo.repository;

import com.footballiq.demo.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface OtpRequestRepository extends JpaRepository<OtpRequest, UUID> {
    void deleteByExpiresAtBefore(LocalDateTime now);
}
