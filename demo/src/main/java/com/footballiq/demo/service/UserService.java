package com.footballiq.demo.service;

import com.footballiq.demo.dto.AuthResponse;
import com.footballiq.demo.dto.LoginRequest;
import com.footballiq.demo.dto.RegisterRequest;

public interface UserService {

    AuthResponse registerUser(RegisterRequest request);

    AuthResponse loginUser(LoginRequest request);

    AuthResponse getUserProfile(Long id);

    AuthResponse updateUserPreferences(Long id, String preferredTeam);
}