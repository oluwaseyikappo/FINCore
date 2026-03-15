package com.fintaxpro.tax.rules;

import com.fintaxpro.tax.enums.FilingStatus;

public class StandardDeduction {

    public static double getStandardDeduction(FilingStatus status) {
        return switch (status) {
            case SINGLE, MARRIED_FILING_SEPARATELY -> 13_850;
            case MARRIED_FILING_JOINTLY, QUALIFYING_SURVIVING_SPOUSE -> 27_700;
            case HEAD_OF_HOUSEHOLD -> 20_800;
        };
    }
}

