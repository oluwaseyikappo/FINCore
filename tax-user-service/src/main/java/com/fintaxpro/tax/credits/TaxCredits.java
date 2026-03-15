package com.fintaxpro.tax.credits;

import com.fintaxpro.tax.enums.FilingStatus;

public class TaxCredits {

    public static double calculateCredits(double income,
                                          FilingStatus status,
                                          int qualifyingChildren) {

        double childTaxCreditPerChild = 2_000;
        return qualifyingChildren * childTaxCreditPerChild;
    }
}

