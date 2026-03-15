package com.fintaxpro.tax.engine;

import com.fintaxpro.tax.enums.FilingStatus;

public class TaxEngine {

    private TaxEngine() {
        // Utility class - no instances
    }

    public static double computeTax(double income,
                                    FilingStatus filingStatus,
                                    int qualifyingChildren) {

        // 1. Basic validation
        if (income < 0) {
            throw new IllegalArgumentException("Income cannot be negative");
        }
        if (filingStatus == null) {
            throw new IllegalArgumentException("Filing status is required");
        }
        if (qualifyingChildren < 0) {
            throw new IllegalArgumentException("Qualifying children cannot be negative");
        }

        // 2. TODO: Apply standard deduction
        // 3. TODO: Compute taxable income
        // 4. TODO: Apply tax brackets
        // 5. TODO: Apply child tax credits

        // Temporary placeholder
        return 0.0;
    }
}
