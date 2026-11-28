package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.model.GeneralPopulationLookups;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Feature 31.
 * Feature 31 shows the population of a specific city in a country.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature31Test {

    static Feature31 feature31;
    static GeneralPopulationLookups validReport;

    /**
     * Runs once before all tests to set up the Feature31 object and sample city data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 31                ");
        System.out.println("******************************************************************");

        feature31 = new Feature31();
        validReport = new GeneralPopulationLookups("Los Angeles (United States)", 3694820L);
    }

    /**
     * Test 1: Checks what happens if the report object is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No population data found for this city.'");
        System.out.println("Actual Result  :");

        feature31.displayReport(null);
    }

    /**
     * Test 2: Checks what happens if the city name inside the report is null.
     * The program should display 'N/A' instead of crashing.
     */
    @Test
    void testDisplayReportNullName() {
        System.out.println("\n--- TEST 2: NULL CITY NAME ---");
        System.out.println("Expected Result: Print report with 'N/A' as City.");
        System.out.println("Actual Result  :");

        GeneralPopulationLookups reportWithNullName = new GeneralPopulationLookups(null, 500000L);
        feature31.displayReport(reportWithNullName);
    }

    /**
     * Test 3: Checks normal behavior with valid city population data.
     * The program should print a clean report for Los Angeles.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 3: VALID REPORT ---");
        System.out.println("Expected Result: Print City Population Report for Los Angeles (United States).");
        System.out.println("Actual Result  :");

        feature31.displayReport(validReport);
    }
}