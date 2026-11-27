package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Feature 13.
 * Feature 13 shows the top N populated cities in a continent.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature13Test {

    static Feature13 feature13;
    static List<City> validCities;
    static final String TEST_CONTINENT = "Asia";
    static final int TEST_LIMIT = 5;

    /**
     * Runs once before all tests to set up the Feature13 object and sample city data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 13                ");
        System.out.println("******************************************************************");

        feature13 = new Feature13();

        // Sample test data mimicking the top 5 populated cities in Asia
        validCities = new ArrayList<>(Arrays.asList(
                new City("Mumbai (Bombay)", "India", "Maharashtra", 10500000),
                new City("Seoul", "South Korea", "Seoul", 9981619),
                new City("Shanghai", "China", "Shanghai", 9696300),
                new City("Jakarta", "Indonesia", "Jakarta Raya", 9604900),
                new City("Karachi", "Pakistan", "Sindh", 9269265)
        ));
    }

    /**
     * Test 1: Checks what happens if the city list is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No cities found for continent: Asia'");
        System.out.println("Actual Result  :");

        feature13.displayReport(null, TEST_CONTINENT, TEST_LIMIT);
    }

    /**
     * Test 2: Checks what happens if the city list is empty.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No cities found for continent: Asia'");
        System.out.println("Actual Result  :");

        List<City> emptyList = new ArrayList<>();
        feature13.displayReport(emptyList, TEST_CONTINENT, TEST_LIMIT);
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

        feature13.displayReport(citiesWithNull, TEST_CONTINENT, TEST_LIMIT);
    }

    /**
     * Test 4: Checks normal behavior with valid city data.
     * The program should print a clean ranked table of all 5 cities.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with top 5 cities in Asia.");
        System.out.println("Actual Result  :");

        feature13.displayReport(validCities, TEST_CONTINENT, TEST_LIMIT);
    }
}