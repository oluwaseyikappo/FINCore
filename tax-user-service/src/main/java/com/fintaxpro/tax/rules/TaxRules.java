package com.fintaxpro.tax.rules;

import com.fintaxpro.tax.enums.FilingStatus;
import com.fintaxpro.tax.model.TaxBracket;

import java.util.List;
import java.util.Map;

public class TaxRules {

    public static Map<FilingStatus, List<TaxBracket>> getBrackets() {
        return Map.of(
                FilingStatus.SINGLE, List.of(
                        new TaxBracket(0,       11_000,   0.10),
                        new TaxBracket(11_000,  44_725,   0.12),
                        new TaxBracket(44_725,  95_375,   0.22),
                        new TaxBracket(95_375,  182_100,  0.24),
                        new TaxBracket(182_100, 231_250,  0.32),
                        new TaxBracket(231_250, 578_125,  0.35),
                        new TaxBracket(578_125, Double.POSITIVE_INFINITY, 0.37)
                )
        );
    }
}

