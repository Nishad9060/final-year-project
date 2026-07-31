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