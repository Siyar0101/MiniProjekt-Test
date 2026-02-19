package test;
import org.junit.jupiter.api.*;

import db.DataAccessException;
import db.ParkingSpotDB;
import model.ParkingSpot;

import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotDBTest {

    private ParkingSpotDB psdb;

    @BeforeEach
    void setup() throws DataAccessException {
        psdb = new ParkingSpotDB();
    }

    // -------------------------------
    // TC1: Valid range, no overlaps → all free spots returned
    // -------------------------------
    @Test
    void testFindAvailable_TC1() throws Exception {
        LocalDate from = LocalDate.of(2026, 2, 1);
        LocalDate to   = LocalDate.of(2026, 2, 10);

        List<ParkingSpot> result = psdb.findAvailable(from, to);

        // Spots 1–4 are occupied in 2026 (see Appendix)
        // Spots 5 and 6 are free
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId() == 5));
        assertTrue(result.stream().anyMatch(p -> p.getId() == 6));
    }

    // -------------------------------
    // TC2: Overlap with spot 1 (rented 2025-01-01 → 2026-12-31)
    // -------------------------------
    @Test
    void testFindAvailable_TC2() throws Exception {
        LocalDate from = LocalDate.of(2025, 5, 1);
        LocalDate to   = LocalDate.of(2025, 6, 1);

        List<ParkingSpot> result = psdb.findAvailable(from, to);

        // Spot 1 is occupied → must NOT be in result
        assertFalse(result.stream().anyMatch(p -> p.getId() == 1));
    }

    // -------------------------------
    // TC3: Boundary test — endDate is exclusive
    // Spot 4 ends 2026-12-31 → request ends same day → should be available
    // -------------------------------
    @Test
    void testFindAvailable_TC3() throws Exception {
        LocalDate from = LocalDate.of(2026, 12, 31);
        LocalDate to   = LocalDate.of(2026, 12, 31); // exclusive

        List<ParkingSpot> result = psdb.findAvailable(from, to);

        // Spot 4 should be available because endDate is exclusive
        assertTrue(result.stream().anyMatch(p -> p.getId() == 4));
    }

    // -------------------------------
    // TC4: No available spots
    // Entire 2026 is fully booked for spots 1–4
    // -------------------------------
    @Test
    void testFindAvailable_TC4() throws Exception {
        LocalDate from = LocalDate.of(2026, 1, 1);
        LocalDate to   = LocalDate.of(2026, 12, 31);

        List<ParkingSpot> result = psdb.findAvailable(from, to);

        // Only spots 5 and 6 are free
        assertEquals(2, result.size());
    }

    // -------------------------------
    // TC5: fromDate > toDate → IllegalArgumentException
    // -------------------------------
    @Test
    void testFindAvailable_TC5() {
        LocalDate from = LocalDate.of(2026, 5, 1);
        LocalDate to   = LocalDate.of(2026, 4, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            psdb.findAvailable(from, to);
        });
    }

    // -------------------------------
    // TC6: Null input → IllegalArgumentException
    // -------------------------------
    @Test
    void testFindAvailable_TC6() {
        LocalDate from = null;
        LocalDate to   = LocalDate.of(2026, 5, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            psdb.findAvailable(from, to);
        });
    }

    @Test
    void testFindAvailable_TC7() {
        LocalDate from = LocalDate.of(2026, 5, 1);
        LocalDate to   = null;

        assertThrows(IllegalArgumentException.class, () -> {
            psdb.findAvailable(from, to);
        });
    }
}
