package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import model.Vehicle;
import controller.FeeCalculator;

public class FeeCalculatorCalcGreenTest {

    FeeCalculator fc = new FeeCalculator();

    // TC1: List not null, contains only electric → true
    @Test
    void testCalcGreen_TC1() {
        List<Vehicle> vv = Arrays.asList(
                new Vehicle(1, "EL11111", true, LocalDate.now())
        );
        assertTrue(fc.calcGreen(vv));
    }

    // TC2: List not null, contains non-electric → false
    @Test
    void testCalcGreen_TC2() {
        List<Vehicle> vv = Arrays.asList(
                new Vehicle(2, "GA22222", false, LocalDate.now())
        );
        assertFalse(fc.calcGreen(vv));
    }

    // TC3: Null list → IllegalArgumentException
    @Test
    void testCalcGreen_TC3() {
        assertThrows(IllegalArgumentException.class, () -> {
            fc.calcGreen(null);
        });
    }

    // TC4: Non-empty list, all electric → true
    @Test
    void testCalcGreen_TC4() {
        List<Vehicle> vv = Arrays.asList(
                new Vehicle(3, "EL33333", true, LocalDate.now()),
                new Vehicle(4, "EL44444", true, LocalDate.now())
        );
        assertTrue(fc.calcGreen(vv));
    }

    // TC5: Empty list → false
    @Test
    void testCalcGreen_TC5() {
        List<Vehicle> vv = new ArrayList<>();
        assertFalse(fc.calcGreen(vv));
    }
}
