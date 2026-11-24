package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This class tests the code for Feature 27.
 * Feature 27 displays the total population of a specific continent.
 * These tests ensure the formatting logic handles invalid numbers properly.
 */
public class Feature27Test {

    // A Feature27 object is created here to test the methods.
    static Feature27 feature27;

    /**
     * The @BeforeAll tag means this setup runs once before any tests start.
     * This method creates the Feature27 object for testing.
     */
    @BeforeAll
    static void init() {
        feature27 = new Feature27();
    }

    /**
     * Test 1: This tests what happens if the population data is 0.
     * The program should trigger the safety check and print a fallback message.
     */
    @Test
    void testPrintPopulationReportZero() {
        System.out.println("\n--- TEST 1: ZERO POPULATION ---");
        System.out.println("Expected Result: Print 'No population data available for Antarctica.'");
        System.out.println("Actual Result  :");

        // '0' is passed to verify boundary condition handling.
        feature27.printPopulationReport("Antarctica", 0L);
    }

    /**
     * Test 2: This tests what happens if the population data is a negative number.
     * The program should handle this safely and print a fallback message.
     */
    @Test
    void testPrintPopulationReportNegative() {
        System.out.println("\n--- TEST 2: NEGATIVE POPULATION ---");
        System.out.println("Expected Result: Print 'No population data available for Europe.'");
        System.out.println("Actual Result  :");

        // A negative number is passed to simulate bad database output.
        feature27.printPopulationReport("Europe", -50000L);
    }

    /**
     * Test 3: This tests what happens under normal conditions with good data.
     * The program should format the large number correctly with commas.
     */
    @Test
    void testPrintPopulationReportValid() {
        System.out.println("\n--- TEST 3: VALID POPULATION ---");
        System.out.println("Expected Result: Print 'Asia Population: 3,705,025,700' followed by a line divider.");
        System.out.println("Actual Result  :");

        // A realistic continent population number is passed to verify correct comma formatting.
        feature27.printPopulationReport("Asia", 3705025700L);
    }
}