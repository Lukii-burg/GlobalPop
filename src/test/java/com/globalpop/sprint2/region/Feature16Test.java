package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Feature 16.
 * Feature 16 shows the top N populated cities in a district.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature16Test {

    static Feature16 feature16;
    static List<City> validCities;
    static final String TEST_DISTRICT = "California";
    static final int TEST_LIMIT = 5;

    /**
     * Runs once before all tests to set up the Feature16 object and sample city data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 16                ");
        System.out.println("******************************************************************");

        feature16 = new Feature16();

        // Sample test data mimicking the top 5 populated cities in California
        validCities = new ArrayList<>(Arrays.asList(
                new City("Los Angeles", "United States", "California", 3694820),
                new City("San Diego", "United States", "California", 1223400),
                new City("San Jose", "United States", "California", 894943),
                new City("San Francisco", "United States", "California", 776733),
                new City("Long Beach", "United States", "California", 461522)
        ));
    }

    /**
     * Test 1: Checks what happens if the city list is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No cities found for district: \"California\"'");
        System.out.println("Actual Result  :");

        feature16.displayReport(null, TEST_DISTRICT, TEST_LIMIT);
    }

    /**
     * Test 2: Checks what happens if the city list is empty.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No cities found for district: \"California\"'");
        System.out.println("Actual Result  :");

        List<City> emptyList = new ArrayList<>();
        feature16.displayReport(emptyList, TEST_DISTRICT, TEST_LIMIT);
    }

    /**
     * Test 3: Checks what happens if there is a null item inside the list.
     * The program should skip the null item and print the rest.
     */
    @Test
    void testDisplayReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 cities and skip the null item safely.");
        System.out.println("Actual Result  :");

        List<City> citiesWithNull = new ArrayList<>(validCities);
        citiesWithNull.add(null);

        feature16.displayReport(citiesWithNull, TEST_DISTRICT, TEST_LIMIT);
    }

    /**
     * Test 4: Checks normal behavior with valid city data.
     * The program should print a clean table of all 5 cities.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 top cities in California.");
        System.out.println("Actual Result  :");

        feature16.displayReport(validCities, TEST_DISTRICT, TEST_LIMIT);
    }
}