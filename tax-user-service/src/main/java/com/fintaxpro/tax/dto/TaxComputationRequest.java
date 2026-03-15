package com.fintaxpro.tax.dto;

import com.fintaxpro.tax.enums.FilingStatus;
import lombok.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for tax computation")
public class TaxComputationRequest {

    @NotNull(message = "Income is required")
    @Min(value = 0, message = "Income cannot be negative")
    @Schema(description = "Total income for the tax year", example = "85000")
    private Double income;

    @NotNull(message = "Filing status is required")
    @Schema(description = "Filing status of the taxpayer", example = "SINGLE")
    private FilingStatus filingStatus;

    @Min(value = 0, message = "Dependents cannot be negative")
    @Schema(description = "Number of dependents (optional)", example = "2")
    private Integer dependents;

    @Min(value = 0, message = "Deduction cannot be negative")
    @Schema(description = "Optional deduction amount", example = "5000")
    private Double deduction;

    @Min(value = 0, message = "Credit cannot be negative")
    @Schema(description = "Optional tax credit", example = "500")
    private Double credit;
}
