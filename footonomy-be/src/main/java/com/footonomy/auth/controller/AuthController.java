package com.footonomy.auth.controller;

import com.footonomy.auth.dto.AuthResponse;
import com.footonomy.auth.dto.LoginRequest;
import com.footonomy.auth.dto.SignupRequest;
import com.footonomy.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Public — no auth. See docs/03_TRD.md §4 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * JWTs are stateless and there's no token blacklist/session store in V1 (not specified in
     * docs/03_TRD.md §6) — the client is responsible for discarding the token. This endpoint
     * exists to satisfy the frozen contract and gives the frontend a clean place to hang any
     * future server-side invalidation without a request-shape change.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
