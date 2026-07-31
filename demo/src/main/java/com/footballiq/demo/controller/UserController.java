package com.footballiq.demo.controller;

import com.footballiq.demo.dto.AuthResponse;
import com.footballiq.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/preferences")
    public ResponseEntity<AuthResponse> updateUserPreferences(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        String preferredTeam = payload.get("preferredTeam");
        AuthResponse response = userService.updateUserPreferences(id, preferredTeam);
        
        if (!response.getIsAuthenticated()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}