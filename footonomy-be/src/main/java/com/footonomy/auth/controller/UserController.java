package com.footonomy.auth.controller;

import com.footonomy.auth.dto.UpdateUserRequest;
import com.footonomy.auth.dto.UserResponse;
import com.footonomy.auth.security.CurrentUserProvider;
import com.footonomy.auth.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Requires a valid JWT — see docs/03_TRD.md §4/§6 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public UserResponse getProfile() {
        return userProfileService.getProfile(currentUserProvider.requireCurrentUserId());
    }

    @PutMapping
    public UserResponse updateProfile(@Valid @RequestBody UpdateUserRequest request) {
        return userProfileService.updateProfile(currentUserProvider.requireCurrentUserId(), request);
    }
}
