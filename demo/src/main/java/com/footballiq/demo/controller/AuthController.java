package com.footballiq.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("isAuthenticated", true);
        response.put("userId", 1);
        response.put("email", "demo@footballiq.com");

        return ResponseEntity.ok(response);
    }
}