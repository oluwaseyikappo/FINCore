import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxEngineTest {

    @Test
    void testCalculateTax() {
        // Arrange
        double income = 50000;
        double expectedTax = 5000; // example expected value

        // Act
        double actualTax = calculateTax(income);

        // Assert
        assertEquals(expectedTax, actualTax, "Tax calculation failed");
    }

    // Temporary placeholder method so the test runs without errors
    // We will replace this with your real engine logic next
    double calculateTax(double income) {
        return income * 0.10; // simple placeholder logic
    }
}
