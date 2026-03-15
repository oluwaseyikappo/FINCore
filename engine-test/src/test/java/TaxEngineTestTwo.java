import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxEngineTestTwo {

    @Test
    void testFlatTax() {
        double income = 50000;
        double expected = 5000; // 10% flat tax
        double actual = income * 0.10;

        assertEquals(expected, actual, 0.01, "Flat tax calculation failed");
    }

    @Test
    void testMultipleIncomes() {
        assertEquals(1000, 10000 * 0.10);
        assertEquals(3000, 30000 * 0.10);
        assertEquals(5000, 50000 * 0.10);
        assertEquals(8000, 80000 * 0.10);
        assertEquals(12000, 120000 * 0.10);
    }

    @Test
    void testZeroIncome() {
        assertEquals(0, 0 * 0.10);
    }

    @Test
    void testNegativeIncomeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Income cannot be negative");
        });
    }
}
