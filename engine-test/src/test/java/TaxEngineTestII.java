import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TaxEngineTestII {

    // -----------------------------
    // 1. PARAMETERIZED BRACKET TESTS
    // -----------------------------
    @ParameterizedTest
    @DisplayName("Verify tax calculation across multiple income brackets")
    @CsvSource({
            "10000, 1000",
            "30000, 3000",
            "50000, 5000",
            "80000, 12000",
            "120000, 24000"
    })
    void testTaxBrackets(double income, double expectedTax) {
        double actual = calculateTax(income);
        assertEquals(expectedTax, actual, 0.01, "Bracket calculation mismatch");
    }

    // -----------------------------
    // 2. STANDARD DEDUCTION TESTS
    // -----------------------------
    @Test
    @DisplayName("Verify standard deduction is applied correctly")
    void testStandardDeduction() {
        double income = 50000;
        double deduction = 12500;
        double expectedTax = (income - deduction) * 0.10;

        double actual = calculateTaxWithDeduction(income, deduction);
        assertEquals(expectedTax, actual, 0.01, "Standard deduction calculation failed");
    }

    // -----------------------------
    // 3. TAX CREDIT TESTS
    // -----------------------------
    @Test
    @DisplayName("Verify tax credits reduce final tax liability")
    void testTaxCredits() {
        double income = 60000;
        double credit = 2000;

        double expected = (income * 0.10) - credit;
        double actual = calculateTaxWithCredit(income, credit);

        assertEquals(expected, actual, 0.01, "Tax credit calculation failed");
    }

    // -----------------------------
    // 4. EDGE CASE TESTS
    // -----------------------------
    @Nested
    @DisplayName("Edge Case Validation")
    class EdgeCases {

        @Test
        @DisplayName("Income cannot be negative")
        void testNegativeIncome() {
            assertThrows(IllegalArgumentException.class,
                    () -> calculateTax(-5000),
                    "Negative income should throw exception");
        }

        @Test
        @DisplayName("Zero income should return zero tax")
        void testZeroIncome() {
            assertEquals(0, calculateTax(0), "Zero income should produce zero tax");
        }
    }

    // ---------------------------------------------------
    // TEMPORARY ENGINE LOGIC (We replace this next step)
    // ---------------------------------------------------
    double calculateTax(double income) {
        if (income < 0) throw new IllegalArgumentException("Income cannot be negative");
        return income * 0.10;
    }

    double calculateTaxWithDeduction(double income, double deduction) {
        if (income < 0 || deduction < 0) throw new IllegalArgumentException("Invalid values");
        double taxable = Math.max(0, income - deduction);
        return taxable * 0.10;
    }

    double calculateTaxWithCredit(double income, double credit) {
        if (income < 0 || credit < 0) throw new IllegalArgumentException("Invalid values");
        double tax = income * 0.10;
        return Math.max(0, tax - credit);
    }
}
