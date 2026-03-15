package com.fintaxpro.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProfileUpdateRequest {

    @Schema(example = "Oluwaseyi")
    private String firstName;

    @Schema(example = "Kappo")
    private String lastName;
}
