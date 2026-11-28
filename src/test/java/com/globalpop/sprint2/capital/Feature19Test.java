package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Feature 19.
 * Feature 19 shows all capital cities in a region, sorted from largest to smallest population.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature19Test {

    static Feature19 feature19;
    static List<CapitalCity> validCapitalCities;
    static final String TEST_REGION = "Southeast Asia";

    /**
     * Runs once before all tests to set up the Feature19 object and sample capital city data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 19                ");
        System.out.println("******************************************************************");

        feature19 = new Feature19();

        // Sample test data mimicking capital cities in Southeast Asia
        validCapitalCities = new ArrayList<>(Arrays.asList(
                new CapitalCity("Jakarta", "Indonesia", 9604900),
                new CapitalCity("Bangkok", "Thailand", 6320174),
                new CapitalCity("Singapore", "Singapore", 4017733),
                new CapitalCity("Rangoon (Yangon)", "Myanmar", 3361700),
                new CapitalCity("Manila", "Philippines", 1581082)
        ));
    }

    /**
     * Test 1: Checks what happens if the capital city list is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No capital cities found for region: Southeast Asia'");
        System.out.println("Actual Result  :");

        feature19.displayReport(null, TEST_REGION);
    }

    /**
     * Test 2: Checks what happens if the capital city list is empty.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No capital cities found for region: Southeast Asia'");
        System.out.println("Actual Result  :");

        List<CapitalCity> emptyList = new ArrayList<>();
        feature19.displayReport(emptyList, TEST_REGION);
    }

    /**
     * Test 3: Checks what happens if there is a null item inside the list.
     * The program should skip the null item and print the rest.
     */
    @Test
    void testDisplayReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 capital cities and skip the null item safely.");
        System.out.println("Actual Result  :");

        List<CapitalCity> capitalCitiesWithNull = new ArrayList<>(validCapitalCities);
        capitalCitiesWithNull.add(null);

        feature19.displayReport(capitalCitiesWithNull, TEST_REGION);
    }

    /**
     * Test 4: Checks normal behavior with valid capital city data.
     * The program should print a clean table of all 5 capital cities.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 capital cities in Southeast Asia.");
        System.out.println("Actual Result  :");

        feature19.displayReport(validCapitalCities, TEST_REGION);
    }

    @Test
    void testDisplayReportWithNullRegion() {
        feature19.displayReport(validCapitalCities, null);
    }

}