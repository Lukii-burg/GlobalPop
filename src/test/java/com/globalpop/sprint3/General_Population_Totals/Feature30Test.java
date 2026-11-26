package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.model.GeneralPopulationLookups;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Feature 30.
 * Feature 30 shows the total population of a district in a country.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature30Test {

    static Feature30 feature30;
    static GeneralPopulationLookups validReport;

    /**
     * Runs once before all tests to set up the Feature30 object and sample district data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 30                ");
        System.out.println("******************************************************************");

        feature30 = new Feature30();
        validReport = new GeneralPopulationLookups("California (United States)", 16716706L);
    }

    /**
     * Test 1: Checks what happens if the report object is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No population data found for this district.'");
        System.out.println("Actual Result  :");

        feature30.displayReport(null);
    }

    /**
     * Test 2: Checks what happens if the district name inside the report is null.
     * The program should display 'N/A' instead of crashing.
     */
    @Test
    void testDisplayReportNullName() {
        System.out.println("\n--- TEST 2: NULL DISTRICT NAME ---");
        System.out.println("Expected Result: Print report with 'N/A' as District.");
        System.out.println("Actual Result  :");

        GeneralPopulationLookups reportWithNullName = new GeneralPopulationLookups(null, 500000L);
        feature30.displayReport(reportWithNullName);
    }

    /**
     * Test 3: Checks normal behavior with valid district population data.
     * The program should print a clean report for California.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 3: VALID REPORT ---");
        System.out.println("Expected Result: Print District Population Report for California (United States).");
        System.out.println("Actual Result  :");

        feature30.displayReport(validReport);
    }
}