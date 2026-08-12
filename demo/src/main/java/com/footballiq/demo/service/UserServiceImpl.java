package com.footballiq.demo.service;

import com.footballiq.demo.dto.AuthResponse;
import com.footballiq.demo.dto.LoginRequest;
import com.footballiq.demo.dto.RegisterRequest;
import com.footballiq.demo.entity.OtpRequest;
import com.footballiq.demo.entity.User;
import com.footballiq.demo.repository.OtpRequestRepository;
import com.footballiq.demo.repository.UserRepository;
import com.footballiq.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final OtpRequestRepository otpRequestRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService, OtpRequestRepository otpRequestRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.otpRequestRepository = otpRequestRepository;
    }

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, null, null, null, request.getEmail(), null, null, "Email already registered!");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, request.getFullName(), request.getPreferredTeam());
        User savedUser = userRepository.save(user);
        
        String jwtToken = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(
                true,
                jwtToken,
                null,
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
            return new AuthResponse(false, null, null, null, request.getEmail(), null, null, "Invalid credentials");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(false, null, null, null, request.getEmail(), null, null, "Invalid credentials");
        }

        String otpCode = emailService.generateOtp();
        OtpRequest otpRequest = new OtpRequest(user.getEmail(), otpCode, LocalDateTime.now().plusMinutes(30));
        otpRequest = otpRequestRepository.save(otpRequest);
        
        emailService.sendOtpEmail(user.getEmail(), otpCode);

        return new AuthResponse(
                true,
                null,
                otpRequest.getId(),
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPreferredTeam(),
                "OTP sent to email. Please verify to complete login."
        );
    }

    @Override
    public AuthResponse verifyOtpLogin(UUID otpId, String otpCode) {
        Optional<OtpRequest> otpOptional = otpRequestRepository.findById(otpId);
        
        if (otpOptional.isEmpty()) {
            return new AuthResponse(false, null, null, null, null, null, null, "Invalid OTP Request");
        }
        
        OtpRequest otpRequest = otpOptional.get();
        
        if (otpRequest.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRequestRepository.delete(otpRequest);
            return new AuthResponse(false, null, null, null, null, null, null, "OTP has expired");
        }
        
        if (!otpRequest.getOtpCode().equals(otpCode)) {
            return new AuthResponse(false, null, null, null, null, null, null, "Incorrect OTP Code");
        }
        
        Optional<User> userOptional = userRepository.findByEmail(otpRequest.getEmail());
        if (userOptional.isEmpty()) {
            return new AuthResponse(false, null, null, null, null, null, null, "User not found");
        }
        
        User user = userOptional.get();
        String jwtToken = jwtUtil.generateToken(user.getEmail());
        
        // Purge OTP record after successful use
        otpRequestRepository.delete(otpRequest);

        return new AuthResponse(
                true,
                jwtToken,
                null,
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
            return new AuthResponse(false, null, null, null, null, null, null, "User not found");
        }

        User user = userOptional.get();
        return new AuthResponse(
                true,
                null,
                null,
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
            return new AuthResponse(false, null, null, null, null, null, null, "User not found");
        }

        User user = userOptional.get();
        user.setPreferredTeam(preferredTeam);
        User updatedUser = userRepository.save(user);

        return new AuthResponse(
                true,
                null,
                null,
                updatedUser.getId(),
                updatedUser.getEmail(),
                updatedUser.getFullName(),
                updatedUser.getPreferredTeam(),
                "Preferences updated successfully"
        );
    }
}