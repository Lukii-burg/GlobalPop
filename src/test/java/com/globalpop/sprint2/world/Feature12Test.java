package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Feature 12.
 * Feature 12 shows the top N populated cities in the world.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature12Test {

    static Feature12 feature12;
    static List<City> validCities;
    static final int TEST_LIMIT = 5;

    /**
     * Runs once before all tests to set up the Feature12 object and sample city data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 12                ");
        System.out.println("******************************************************************");

        feature12 = new Feature12();

        // Sample test data mimicking the top 5 populated cities in the world
        validCities = new ArrayList<>(Arrays.asList(
                new City("Mumbai (Bombay)", "India", "Maharashtra", 10500000),
                new City("Seoul", "South Korea", "Seoul", 9981619),
                new City("São Paulo", "Brazil", "São Paulo", 9968485),
                new City("Shanghai", "China", "Shanghai", 9696300),
                new City("Jakarta", "Indonesia", "Jakarta Raya", 9604900)
        ));
    }

    /**
     * Test 1: Checks what happens if the city list is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No cities found.'");
        System.out.println("Actual Result  :");

        feature12.displayReport(null, TEST_LIMIT);
    }

    /**
     * Test 2: Checks what happens if the city list is empty.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No cities found.'");
        System.out.println("Actual Result  :");

        List<City> emptyList = new ArrayList<>();
        feature12.displayReport(emptyList, TEST_LIMIT);
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

        feature12.displayReport(citiesWithNull, TEST_LIMIT);
    }

    /**
     * Test 4: Checks normal behavior with valid city data.
     * The program should print a clean table of all 5 cities.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 top world cities.");
        System.out.println("Actual Result  :");

        feature12.displayReport(validCities, TEST_LIMIT);
    }
}