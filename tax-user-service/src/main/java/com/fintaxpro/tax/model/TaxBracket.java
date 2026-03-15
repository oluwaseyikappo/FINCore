package com.fintaxpro.tax.model;

public class TaxBracket {

    private final double lowerBound;
    private final double upperBound;
    private final double rate;

    public TaxBracket(double lowerBound, double upperBound, double rate) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.rate = rate;
    }

    public double getLowerBound() { return lowerBound; }
    public double getUpperBound() { return upperBound; }
    public double getRate() { return rate; }
}
