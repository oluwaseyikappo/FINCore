package com.fintaxpro.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String createdAt;
}
