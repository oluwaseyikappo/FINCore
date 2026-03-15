package com.fintaxpro.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginResponse {

    @Schema(description = "User email", example = "seyi@example.com")
    private String email;

    @Schema(description = "JWT token returned after successful login",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
