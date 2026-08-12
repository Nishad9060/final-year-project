package com.footballiq.demo.dto;

import java.util.UUID;

public class OtpVerifyRequest {
    private UUID otpId;
    private String otpCode;

    public OtpVerifyRequest() {
    }

    public OtpVerifyRequest(UUID otpId, String otpCode) {
        this.otpId = otpId;
        this.otpCode = otpCode;
    }

    public UUID getOtpId() {
        return otpId;
    }

    public void setOtpId(UUID otpId) {
        this.otpId = otpId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}
