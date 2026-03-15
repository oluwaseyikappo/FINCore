package com.fintaxpro.user.service;

import com.fintaxpro.user.core.entities.User;
import com.fintaxpro.user.dto.LoginResponse;
import com.fintaxpro.user.dto.LoginRequest;
import com.fintaxpro.user.dto.ProfileUpdateRequest;
import com.fintaxpro.user.dto.ChangePasswordRequest;
import com.fintaxpro.user.repository.UserRepository;
import com.fintaxpro.user.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // REGISTER
    public User registerUser(User user) {

        log.info("Attempting to register user with email={}", user.getEmail());

        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Registration failed — email already exists: {}", user.getEmail());
            throw new RuntimeException("Email already exists");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        log.info("User registered successfully: {}", savedUser.getEmail());
        return savedUser;
    }

    // LOGIN
    public LoginResponse login(LoginRequest request) {

        log.info("Login attempt for email={}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed — invalid email: {}", request.getEmail());
                    return new RuntimeException("Invalid email or password");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            log.warn("Login failed — incorrect password for email={}", request.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        log.info("Login successful for email={}", request.getEmail());
        return new LoginResponse(user.getEmail(), token);
    }

    // ⭐ UPDATE PROFILE
    public User updateProfile(String email, ProfileUpdateRequest request) {

        log.info("Updating profile for user={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Profile update failed — user not found: {}", email);
                    return new RuntimeException("User not found");
                });

        if (request.getFirstName() != null) {
            log.debug("Updating first name for user={}", email);
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            log.debug("Updating last name for user={}", email);
            user.setLastName(request.getLastName());
        }

        user.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(user);

        log.info("Profile updated successfully for user={}", email);
        return updated;
    }

    // ⭐ CHANGE PASSWORD
    public void changePassword(String email, ChangePasswordRequest request) {

        log.info("Password change requested for user={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Password change failed — user not found: {}", email);
                    return new RuntimeException("User not found");
                });

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            log.warn("Password change failed — incorrect old password for user={}", email);
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        log.info("Password changed successfully for user={}", email);
    }

    // FIND BY EMAIL
    public User findByEmail(String email) {

        log.debug("Fetching user by email={}", email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User lookup failed — not found: {}", email);
                    return new RuntimeException("User not found");
                });
    }
}
