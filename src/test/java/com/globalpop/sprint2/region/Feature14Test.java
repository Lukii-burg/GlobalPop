package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class tests the code for Feature 14.
 * Feature 14 shows the top N populated cities in a region.
 * These tests ensure the program does not crash when there are mistakes in the data.
 */
public class Feature14Test {

    // A Feature14 object is created here to test the methods.
    static Feature14 feature14;

    // A reusable list of valid city data for the tests.
    static List<City> validCities;

    /**
     * The @BeforeAll tag means this setup runs once before any tests start.
     * This method creates the Feature14 object and initializes the reusable test data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 14                ");
        System.out.println("******************************************************************");

        feature14 = new Feature14();

        // The valid test data is initialized here so it can be reused across any test method.
        // Data mimics a "Top 5 in Southeast Asia" result.
        validCities = new ArrayList<>(Arrays.asList(
                new City("Jakarta", "Indonesia", "Jakarta Raya", 9604900),
                new City("Bangkok", "Thailand", "Bangkok", 4329864),
                new City("Yangon", "Myanmar", "Yangon", 3986000),
                new City("Ho Chi Minh City", "Vietnam", "Ho Chi Minh", 3981500),
                new City("Singapore", "Singapore", "Singapore", 3567000)
        ));
    }

    /**
     * Test 1: This tests what happens if the data is completely missing (null).
     * The program should handle this safely and not crash.
     */
    @Test
    void testPrintCityReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No city data available to display for this region.'");
        System.out.println("Actual Result  :");

        // 'null' is passed directly instead of a real list.
        feature14.printCityReport(null);
    }

    /**
     * Test 2: This tests what happens if an empty list is provided.
     * The program should print a message saying there is no data to show.
     */
    @Test
    void testPrintCityReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No city data available to display for this region.'");
        System.out.println("Actual Result  :");

        // A list is created, but no cities are put inside it.
        List<City> cities = new ArrayList<>();

        // The empty list is passed to the method.
        feature14.printCityReport(cities);
    }

    /**
     * Test 3: This tests what happens if the list has a missing (null) city inside it.
     * The program should skip the missing city and not crash.
     */
    @Test
    void testPrintCityReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 cities. Skip the null element safely. Total Listed: 5.");
        System.out.println("Actual Result  :");

        // A new list is created by copying the valid data, then a null element is added to test the safety check.
        List<City> citiesWithNull = new ArrayList<>(validCities);
        citiesWithNull.add(null);

        // This mixed list is passed to the method to verify it does not crash.
        feature14.printCityReport(citiesWithNull);
    }

    /**
     * Test 4: This tests what happens under normal conditions with good data.
     * The program should print a nice table with all the provided cities.
     */
    @Test
    void testPrintCityReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 cities. Total Listed: 5.");
        System.out.println("Actual Result  :");

        // The reusable valid list is passed directly to the method.
        feature14.printCityReport(validCities);
    }
}