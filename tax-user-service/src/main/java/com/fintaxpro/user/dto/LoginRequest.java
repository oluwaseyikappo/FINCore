package com.fintaxpro.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    @Schema(description = "User's registered email", example = "seyi@example.com")
    private String email;

    @Schema(description = "User's account password", example = "StrongPass123!")
    private String password;

    // ⭐ REQUIRED: No‑args constructor for Spring to bind JSON
    public LoginRequest() {
    }

    // ⭐ Optional: All‑args constructor (not required but useful)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
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
