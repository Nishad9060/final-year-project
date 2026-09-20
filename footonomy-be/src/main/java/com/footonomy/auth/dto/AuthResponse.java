package com.footonomy.auth.dto;

public record AuthResponse(String token, UserResponse user) {
}
