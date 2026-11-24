package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This class tests the code for Feature 26.
 * Feature 26 displays the total population of the world.
 * These tests ensure the formatting logic handles invalid numbers properly.
 */
public class Feature26Test {

    // A Feature26 object is created here to test the methods.
    static Feature26 feature26;

    /**
     * The @BeforeAll tag means this setup runs once before any tests start.
     * This method creates the Feature26 object for testing.
     */
    @BeforeAll
    static void init() {
        feature26 = new Feature26();
    }

    /**
     * Test 1: This tests what happens if the population data is 0.
     * The program should trigger the safety check and print a fallback message.
     */
    @Test
    void testPrintPopulationReportZero() {
        System.out.println("\n--- TEST 1: ZERO POPULATION ---");
        System.out.println("Expected Result: Print 'No population data available.'");
        System.out.println("Actual Result  :");

        // '0' is passed to verify boundary condition handling
        feature26.printPopulationReport(0L);
    }

    /**
     * Test 2: This tests what happens if the population data is a negative number.
     * The program should handle this safely and print a fallback message.
     */
    @Test
    void testPrintPopulationReportNegative() {
        System.out.println("\n--- TEST 2: NEGATIVE POPULATION ---");
        System.out.println("Expected Result: Print 'No population data available.'");
        System.out.println("Actual Result  :");

        // A negative number is passed to simulate bad database output
        feature26.printPopulationReport(-50000L);
    }

    /**
     * Test 3: This tests what happens under normal conditions with good data.
     * The program should format the large number correctly with commas.
     */
    @Test
    void testPrintPopulationReportValid() {
        System.out.println("\n--- TEST 3: VALID POPULATION ---");
        System.out.println("Expected Result: Print 'World Population: 6,078,749,450' followed by a line divider.");
        System.out.println("Actual Result  :");

        // A realistic world population number is passed to verify correct comma formatting
        feature26.printPopulationReport(6078749450L);
    }
}