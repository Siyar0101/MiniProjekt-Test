package test;
import org.junit.jupiter.api.Test;

import controller.FeeCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class FeeCalculatorTest {

    FeeCalculator fc = new FeeCalculator();

    // TC1: Valid base fee (300), valid seniority (0), hasElectric = false
    @Test
    void testCalcFee_TC1() {
        double result = fc.calcFee(300, 0, false);
        assertEquals(300, result);
    }

    // TC2: Base fee > 500 (502)
    @Test
    void testCalcFee_TC2() {
        double result = fc.calcFee(502, 0, false);
        assertEquals(502, result);
    }

    // TC3: Negative base fee → IllegalArgumentException
    @Test
    void testCalcFee_TC3() {
        assertThrows(IllegalArgumentException.class, () -> {
            fc.calcFee(-10, 0, false);
        });
    }

    // TC4: Invalid seniority = 2
    @Test
    void testCalcFee_TC4() {
        double result = fc.calcFee(300, 2, false);
        assertEquals(300, result);
    }

    // TC5: Negative seniority → IllegalArgumentException
    @Test
    void testCalcFee_TC5() {
        assertThrows(IllegalArgumentException.class, () -> {
            fc.calcFee(300, -1, false);
        });
    }

    // TC6: hasElectric = true → discount applies
    @Test
    void testCalcFee_TC6() {
        double result = fc.calcFee(240, 0, true);
        assertEquals(240 * 0.8, result);
    }

    // TC7: hasElectric = false (valid case)
    @Test
    void testCalcFee_TC7() {
        double result = fc.calcFee(300, 0, false);
        assertEquals(300, result);
    }

    // TC8: Combined valid inputs
    @Test
    void testCalcFee_TC8() {
        double result = fc.calcFee(400, 3, false);
        assertEquals(400 * 0.95, result);
    }

    // TC9: baseMonthlyFee = null
    @Test
    void testCalcFee_TC9() {
        assertThrows(IllegalArgumentException.class, () -> {
            fc.calcFee(-1, 0, false);
        });
    }
}
