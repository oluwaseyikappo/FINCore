/**import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TaxEngineTestEnterprise {

    TaxEngine engine = new TaxEngine();

    // -----------------------------
    // 1. PROGRESSIVE BRACKET TESTS
    // -----------------------------
    @ParameterizedTest
    @DisplayName("Verify progressive tax brackets")
    @CsvSource({
            "10000, 1000",
            "30000, 3000",
            "50000, 7000",
            "80000, 15000",
            "120000, 29000"
    })
    void testProgressiveBrackets(double income, double expectedTax) {
        double actual = engine.calculateTax(income);
        assertEquals(expectedTax, actual, 0.01, "Progressive bracket mismatch");
    }

    // -----------------------------
    // 2. STANDARD DEDUCTION TEST
    // -----------------------------
    @Test
    @DisplayName("Standard deduction reduces taxable income")
    void testStandardDeduction() {
        double income = 60000;
        double deduction = 10000;

        double expected = engine.calculateTax(50000); // 60000 - 10000
        double actual = engine.calculateTaxWithDeduction(income, deduction);

        assertEquals(expected, actual, 0.01);
    }

    // -----------------------------
    // 3. TAX CREDIT TEST
    // -----------------------------
    @Test
    @DisplayName("Tax credits reduce final liability")
    void testTaxCredit() {
        double income = 70000;
        double credit = 2000;

        double expected = Math.max(0, engine.calculateTax(income) - credit);
        double actual = engine.calculateTaxWithCredit(income, credit);

        assertEquals(expected, actual, 0.01);
    }

    // -----------------------------
    // 4. EDGE CASES
    // -----------------------------
    @Nested
    class EdgeCases {

        @Test
        @DisplayName("Negative income throws exception")
        void testNegativeIncome() {
            assertThrows(IllegalArgumentException.class,
                    () -> engine.calculateTax(-5000));
        }

        @Test
        @DisplayName("Zero income returns zero tax")
        void testZeroIncome() {
            assertEquals(0, engine.calculateTax(0));
        }
    }
}
**/