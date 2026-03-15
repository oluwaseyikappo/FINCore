package com.fintaxpro.user.controller;

import com.fintaxpro.user.core.entities.User;
import com.fintaxpro.user.dto.LoginRequest;
import com.fintaxpro.user.dto.LoginResponse;
import com.fintaxpro.user.dto.ProfileResponse;
import com.fintaxpro.user.dto.ProfileUpdateRequest;
import com.fintaxpro.user.dto.ChangePasswordRequest;
import com.fintaxpro.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    // REGISTER
    @Tag(name = "Authentication")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        log.info("Received registration request for email={}", user.getEmail());
        User createdUser = userService.registerUser(user);
        log.info("User registered successfully: email={}", createdUser.getEmail());
        return ResponseEntity.ok(createdUser);
    }

    // LOGIN
    @Tag(name = "Authentication")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for email={}", request.getEmail());
        LoginResponse response = userService.login(request);
        log.info("Login successful for email={}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    // TEST PROTECTED ENDPOINT
    @Tag(name = "Admin")
    @GetMapping("/all")
    public ResponseEntity<String> getAllUsers() {
        log.info("Admin accessed protected endpoint /users/all");
        return ResponseEntity.ok("You accessed a protected endpoint!");
    }

    // GET PROFILE
    @Operation(
            summary = "Get logged-in user's profile",
            description = "Returns the authenticated user's profile using the JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid or missing token"),
            @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions")
    })
    @Tag(name = "Profile")
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.info("Fetching profile for logged-in user: {}", email);

        User user = userService.findByEmail(email);

        ProfileResponse response = new ProfileResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt().toString()
        );

        log.info("Profile retrieved successfully for user: {}", email);

        return ResponseEntity.ok(response);
    }

    // ⭐ UPDATE PROFILE
    @Operation(
            summary = "Update logged-in user's profile",
            description = "Allows the authenticated user to update their first and last name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid or missing token"),
            @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions")
    })
    @Tag(name = "Profile")
    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody ProfileUpdateRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.info("Updating profile for user: {}", email);

        User updatedUser = userService.updateProfile(email, request);

        ProfileResponse response = new ProfileResponse(
                updatedUser.getEmail(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getCreatedAt().toString()
        );

        log.info("Profile updated successfully for user: {}", email);

        return ResponseEntity.ok(response);
    }

    // ⭐ CHANGE PASSWORD
    @Operation(
            summary = "Change logged-in user's password",
            description = "Allows the authenticated user to change their password by providing the old and new password"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid or missing token"),
            @ApiResponse(responseCode = "403", description = "Forbidden — insufficient permissions")
    })
    @Tag(name = "Profile")
    @PutMapping("/me/password")
    public ResponseEntity<?> changeMyPassword(@RequestBody ChangePasswordRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.info("Password change requested for user: {}", email);

        userService.changePassword(email, request);

        log.info("Password changed successfully for user: {}", email);

        return ResponseEntity.ok("Password updated successfully");
    }
}
