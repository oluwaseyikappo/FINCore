package org.example.taxengine;

public class TaxEngine {

    public double calculateTax(double income) {
        if (income < 0) {
            throw new IllegalArgumentException("Income cannot be negative");
        }

        if (income <= 30000) {
            return income * 0.10;
        } else if (income <= 60000) {
            return (30000 * 0.10) + ((income - 30000) * 0.20);
        } else if (income <= 100000) {
            return (30000 * 0.10) + (30000 * 0.20) + ((income - 60000) * 0.30);
        } else {
            return (30000 * 0.10) + (30000 * 0.20) + (40000 * 0.30) + ((income - 100000) * 0.40);
        }
    }

    public double calculateTaxWithDeduction(double income, double deduction) {
        if (income < 0 || deduction < 0) {
            throw new IllegalArgumentException("Invalid values");
        }

        double taxable = Math.max(0, income - deduction);
        return calculateTax(taxable);
    }

    public double calculateTaxWithCredit(double income, double credit) {
        if (income < 0 || credit < 0) {
            throw new IllegalArgumentException("Invalid values");
        }

        double tax = calculateTax(income);
        return Math.max(0, tax - credit);
    }
}
