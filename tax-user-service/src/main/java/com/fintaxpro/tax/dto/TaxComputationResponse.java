package com.fintaxpro.tax.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response object for tax computation")
public class TaxComputationResponse {

    @Schema(description = "Total tax calculated before credits", example = "12450.75")
    private Double taxAmount;

    @Schema(description = "Total tax credits applied", example = "2000.00")
    private Double taxCredit;

    @Schema(description = "Final tax after applying credits", example = "10450.75")
    private Double netTax;

    @Schema(description = "Refund (positive) or amount owed (negative)", example = "-350.00")
    private Double refundOrOwed;
}
