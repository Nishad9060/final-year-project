package com.footballiq.demo.controller;

import com.footballiq.demo.dto.AuthResponse;
import com.footballiq.demo.dto.LoginRequest;
import com.footballiq.demo.dto.RegisterRequest;
import com.footballiq.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request) {
        AuthResponse response = userService.registerUser(request);
        if (!response.getIsAuthenticated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        AuthResponse response = userService.loginUser(request);
        if (!response.getIsAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response); // Now returns AuthResponse with otpId and message to check email
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@RequestBody com.footballiq.demo.dto.OtpVerifyRequest request) {
        AuthResponse response = userService.verifyOtpLogin(request.getOtpId(), request.getOtpCode());
        if (!response.getIsAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@RequestParam(value = "userId", defaultValue = "1") Long userId) {
        AuthResponse response = userService.getUserProfile(userId);
        if (!response.getIsAuthenticated()) {
            return ResponseEntity.ok(new AuthResponse(true, null, null, 1L, "demo@footballiq.com", "Demo User", "Default Team", "Fallback mock status"));
        }
        return ResponseEntity.ok(response);
    }
}