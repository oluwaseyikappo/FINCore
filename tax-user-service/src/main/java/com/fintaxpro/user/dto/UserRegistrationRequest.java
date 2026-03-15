package com.fintaxpro.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserRegistrationRequest {
    @Schema(description = "User's first name", example = "Oluwaseyi")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "User's last name", example = "Kappo")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "Valid email address for the user", example = "seyi@example.com")
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Password for the new account (minimum 6 characters)", example = "StrongPass123!")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Getters and setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

