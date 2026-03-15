package com.fintaxpro.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @Schema(example = "oldPassword123")
    private String oldPassword;

    @Schema(example = "newSecurePassword456")
    private String newPassword;
}
