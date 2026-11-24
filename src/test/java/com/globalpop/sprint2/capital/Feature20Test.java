package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class tests the code for Feature 20.
 * Feature 20 shows the top N populated capital cities in the world.
 * These tests ensure the program does not crash when there are mistakes in the data.
 */
public class Feature20Test {

    // A Feature20 object is created here to test the methods.
    static Feature20 feature20;

    // A reusable list of valid capital city data for the tests.
    static List<CapitalCity> validCapitalCities;

    /**
     * The @BeforeAll tag means this setup runs once before any tests start.
     * This method creates the Feature20 object and initializes the reusable test data.
     */
    @BeforeAll
    static void init() {
        feature20 = new Feature20();

        // The valid test data is initialized here so it can be reused across any test method.
        // Data mimics a "Top 5" globally populated capital cities result.
        validCapitalCities = new ArrayList<>(Arrays.asList(
                new CapitalCity("Seoul", "South Korea", 9981619),
                new CapitalCity("Jakarta", "Indonesia", 9604900),
                new CapitalCity("Ciudad de México", "Mexico", 8591309),
                new CapitalCity("Moscow", "Russian Federation", 8389200),
                new CapitalCity("Tokyo", "Japan", 7980230)
        ));
    }

    /**
     * Test 1: This tests what happens if the data is completely missing (null).
     * The program should handle this safely and not crash.
     */
    @Test
    void testPrintCapitalCityReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No capital city data available to display.'");
        System.out.println("Actual Result  :");

        // 'null' is passed directly instead of a real list.
        feature20.printCapitalCityReport(null);
    }

    /**
     * Test 2: This tests what happens if an empty list is provided.
     * The program should print a message saying there is no data to show.
     */
    @Test
    void testPrintCapitalCityReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No capital city data available to display.'");
        System.out.println("Actual Result  :");

        // A list is created, but no capital cities are put inside it.
        List<CapitalCity> capitalCities = new ArrayList<>();

        // The empty list is passed to the method.
        feature20.printCapitalCityReport(capitalCities);
    }

    /**
     * Test 3: This tests what happens if the list has a missing (null) capital city inside it.
     * The program should skip the missing item and not crash.
     */
    @Test
    void testPrintCapitalCityReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 capital cities. Skip the null element safely. Total Listed: 5.");
        System.out.println("Actual Result  :");

        // A new list is created by copying the valid data, then a null element is added to test the safety check.
        List<CapitalCity> capitalCitiesWithNull = new ArrayList<>(validCapitalCities);
        capitalCitiesWithNull.add(null);

        // This mixed list is passed to the method to verify it does not crash.
        feature20.printCapitalCityReport(capitalCitiesWithNull);
    }

    /**
     * Test 4: This tests what happens under normal conditions with good data.
     * The program should print a nice table with all the provided capital cities.
     */
    @Test
    void testPrintCapitalCityReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 capital cities. Total Listed: 5.");
        System.out.println("Actual Result  :");

        // The reusable valid list is passed directly to the method.
        feature20.printCapitalCityReport(validCapitalCities);
    }
}