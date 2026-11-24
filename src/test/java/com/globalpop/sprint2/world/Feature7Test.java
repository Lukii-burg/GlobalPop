package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class tests the code for Feature 7.
 * Feature 7 shows all cities in the world from largest population to smallest.
 * These tests ensure the program does not crash when there are mistakes in the data.
 */
public class Feature7Test {

    // A Feature7 object is created here to test the methods.
    static Feature7 feature7;

    // A reusable list of valid city data for the tests.
    static List<City> validCities;

    /**
     * The @BeforeAll tag means this setup runs once before any tests start.
     * This method creates the Feature7 object and initializes the reusable test data.
     */
    @BeforeAll
    static void init() {
        feature7 = new Feature7();

        // The valid test data is initialized here so it can be reused across any test method.
        validCities = new ArrayList<>(Arrays.asList(
                new City("Mumbai (Bombay)", "India", "Maharashtra", 10500000),
                new City("Seoul", "South Korea", "Seoul", 9981619),
                new City("São Paulo", "Brazil", "São Paulo", 9968485),
                new City("Shanghai", "China", "Shanghai", 9696300),
                new City("Jakarta", "Indonesia", "Jakarta Raya", 9604900),
                new City("Yangon", "Myanmar", "Yangon", 3986000)
        ));
    }

    /**
     * Test 1: This tests what happens if the data is completely missing (null).
     * The program should handle this safely and not crash.
     */
    @Test
    void testPrintCityReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No city data available to display.'");
        System.out.println("Actual Result  :");

        // 'null' is passed directly instead of a real list.
        feature7.printCityReport(null);
    }

    /**
     * Test 2: This tests what happens if an empty list is provided.
     * The program should print a message saying there is no data to show.
     */
    @Test
    void testPrintCityReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No city data available to display.'");
        System.out.println("Actual Result  :");

        // A list is created, but no cities are put inside it.
        List<City> cities = new ArrayList<>();

        // The empty list is passed to the method.
        feature7.printCityReport(cities);
    }

    /**
     * Test 3: This tests what happens if the list has a missing (null) city inside it.
     * The program should skip the missing city and not crash.
     */
    @Test
    void testPrintCityReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 6 cities. Skip the null element safely. Total Listed: 6.");
        System.out.println("Actual Result  :");

        // A new list is created by copying the valid data, then a null element is added to test the safety check.
        List<City> citiesWithNull = new ArrayList<>(validCities);
        citiesWithNull.add(null);

        // This mixed list is passed to the method to verify it does not crash.
        feature7.printCityReport(citiesWithNull);
    }

    /**
     * Test 4: This tests what happens under normal conditions with good data.
     * The program should print a nice table with all the provided cities.
     */
    @Test
    void testPrintCityReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 6 cities. Total Listed: 6.");
        System.out.println("Actual Result  :");

        // The reusable valid list is passed directly to the method.
        feature7.printCityReport(validCities);
    }
}