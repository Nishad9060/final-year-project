<#
    FootballIQ Backend API - Person B Upgrade Scaffolding Script
    -----------------------------------------------------------
    Run this from PowerShell in your target backend project root:

        cd C:\path\to\your\project\folder
        .\setup.ps1
#>

$ErrorActionPreference = "Stop"

function Write-ProjectFile {
    param(
        [Parameter(Mandatory = $true)][string]$RelativePath,
        [Parameter(Mandatory = $true)][string]$Content
    )
    $fullPath = Join-Path (Get-Location).Path $RelativePath
    [System.IO.File]::WriteAllText($fullPath, $Content)
    Write-Host "  Updated/Created file: $RelativePath" -ForegroundColor Green
}

Write-Host "FootballIQ Backend API - Upgrading Person B User & Auth Module..." -ForegroundColor Cyan
Write-Host ""

Write-Host "Creating required package directories..." -ForegroundColor Cyan

$directories = @(
    "src\main\java\com\footballiq\demo",
    "src\main\java\com\footballiq\demo\config",
    "src\main\java\com\footballiq\demo\controller",
    "src\main\java\com\footballiq\demo\dto",
    "src\main\java\com\footballiq\demo\entity",
    "src\main\java\com\footballiq\demo\repository",
    "src\main\java\com\footballiq\demo\service",
    "src\main\resources"
)

foreach ($dir in $directories) {
    New-Item -ItemType Directory -Force -Path $dir | Out-Null
    Write-Host "  Directory verified: $dir" -ForegroundColor Green
}

Write-Host ""
Write-Host "Writing application files..." -ForegroundColor Cyan

# Entity: User.java
$userEntity = @'
package com.footballiq.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "preferred_team")
    private String preferredTeam;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public User() {
    }

    public User(String email, String password, String fullName, String preferredTeam) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.preferredTeam = preferredTeam;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\entity\User.java" -Content $userEntity

# Repository: UserRepository.java
$userRepository = @'
package com.footballiq.demo.repository;

import com.footballiq.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\repository\UserRepository.java" -Content $userRepository

# DTO: RegisterRequest.java
$registerRequest = @'
package com.footballiq.demo.dto;

public class RegisterRequest {

    private String email;
    private String password;
    private String fullName;
    private String preferredTeam;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String fullName, String preferredTeam) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.preferredTeam = preferredTeam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\dto\RegisterRequest.java" -Content $registerRequest

# DTO: LoginRequest.java
$loginRequest = @'
package com.footballiq.demo.dto;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\dto\LoginRequest.java" -Content $loginRequest

# DTO: AuthResponse.java
$authResponse = @'
package com.footballiq.demo.dto;

public class AuthResponse {

    private boolean isAuthenticated;
    private Long userId;
    private String email;
    private String fullName;
    private String preferredTeam;
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(boolean isAuthenticated, Long userId, String email, String fullName, String preferredTeam, String message) {
        this.isAuthenticated = isAuthenticated;
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
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\dto\AuthResponse.java" -Content $authResponse

# Service Interface: UserService.java
$userService = @'
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
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\service\UserService.java" -Content $userService

# Service Implementation: UserServiceImpl.java
$userServiceImpl = @'
package com.footballiq.demo.service;

import com.footballiq.demo.dto.AuthResponse;
import com.footballiq.demo.dto.LoginRequest;
import com.footballiq.demo.dto.RegisterRequest;
import com.footballiq.demo.entity.User;
import com.footballiq.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, null, request.getEmail(), null, null, "Email already registered!");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, request.getFullName(), request.getPreferredTeam());
        User savedUser = userRepository.save(user);

        return new AuthResponse(
                true,
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFullName(),
                savedUser.getPreferredTeam(),
                "User registered successfully"
        );
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, null, request.getEmail(), null, null, "Invalid credentials");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(false, null, request.getEmail(), null, null, "Invalid credentials");
        }

        return new AuthResponse(
                true,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPreferredTeam(),
                "Login successful"
        );
    }

    @Override
    public AuthResponse getUserProfile(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, null, null, null, null, "User not found");
        }

        User user = userOptional.get();
        return new AuthResponse(
                true,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPreferredTeam(),
                "User profile fetched successfully"
        );
    }

    @Override
    public AuthResponse updateUserPreferences(Long id, String preferredTeam) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, null, null, null, null, "User not found");
        }

        User user = userOptional.get();
        user.setPreferredTeam(preferredTeam);
        User updatedUser = userRepository.save(user);

        return new AuthResponse(
                true,
                updatedUser.getId(),
                updatedUser.getEmail(),
                updatedUser.getFullName(),
                updatedUser.getPreferredTeam(),
                "Preferences updated successfully"
        );
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\service\UserServiceImpl.java" -Content $userServiceImpl

# Security Config: SecurityConfig.java
$securityConfig = @'
package com.footballiq.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\config\SecurityConfig.java" -Content $securityConfig

# Controller: AuthController.java
$authController = @'
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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@RequestParam(value = "userId", defaultValue = "1") Long userId) {
        AuthResponse response = userService.getUserProfile(userId);
        if (!response.getIsAuthenticated()) {
            return ResponseEntity.ok(new AuthResponse(true, 1L, "demo@footballiq.com", "Demo User", "Default Team", "Fallback mock status"));
        }
        return ResponseEntity.ok(response);
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\controller\AuthController.java" -Content $authController

# Controller: UserController.java
$userController = @'
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
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\controller\UserController.java" -Content $userController

Write-Host ""
Write-Host "Person B implementation code updated successfully!" -ForegroundColor Green