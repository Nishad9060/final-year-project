package com.footballiq.demo.dto;

import java.util.UUID;

public class AuthResponse {

    private boolean isAuthenticated;
    private String token;
    private UUID otpId;
    private Long userId;
    private String email;
    private String fullName;
    private String preferredTeam;
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(boolean isAuthenticated, String token, UUID otpId, Long userId, String email, String fullName, String preferredTeam, String message) {
        this.isAuthenticated = isAuthenticated;
        this.token = token;
        this.otpId = otpId;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.preferredTeam = preferredTeam;
        this.message = message;
    }

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPreferredTeam() {
        return preferredTeam;
    }

    public void setPreferredTeam(String preferredTeam) {
        this.preferredTeam = preferredTeam;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getOtpId() {
        return otpId;
    }

    public void setOtpId(UUID otpId) {
        this.otpId = otpId;
    }
}