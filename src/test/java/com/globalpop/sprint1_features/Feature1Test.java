package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class tests the code for Feature 1.
 * Feature 1 shows all countries in the world from largest population to smallest.
 * These tests ensure the program does not crash when there are mistakes in the data.
 */
public class Feature1Test {

    // A Feature1 object is created here to test the methods.
    static Feature1 feature1;

    // A reusable list of valid country data for the tests.
    static List<Country> validCountries;

    /**
     * The @BeforeAll tag means this setup runs once before any tests start.
     * This method creates the Feature1 object and initializes the reusable test data.
     */
    @BeforeAll
    static void init() {
        feature1 = new Feature1();

        // The valid test data is initialized here so it can be reused across any test method.
        validCountries = new ArrayList<>(Arrays.asList(
                new Country("CHN", "China", "Asia", "Eastern Asia", 1400000000L, "Beijing"),
                new Country("IND", "India", "Asia", "Southern Asia", 1380000000L, "New Delhi"),
                new Country("USA", "United States", "North America", "Northern America", 331000000L, "Washington D.C."),
                new Country("IDN", "Indonesia", "Asia", "Southeast Asia", 273000000L, "Jakarta"),
                new Country("BRA", "Brazil", "South America", "South America", 212000000L, "Brasília"),
                new Country("MMR", "Myanmar", "Asia", "Southeast Asia", 54409800L, "Naypyidaw")
        ));
    }

    /**
     * Test 1: This tests what happens if the data is completely missing (null).
     * The program should handle this safely and not crash.
     */
    @Test
    void testPrintCountryReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No country data available to display.'");
        System.out.println("Actual Result  :");

        // 'null' is passed directly instead of a real list.
        feature1.printCountryReport(null);
    }

    /**
     * Test 2: This tests what happens if an empty list is provided.
     * The program should print a message saying there is no data to show.
     */
    @Test
    void testPrintCountryReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No country data available to display.'");
        System.out.println("Actual Result  :");

        // A list is created, but no countries are put inside it.
        List<Country> countries = new ArrayList<>();

        // The empty list is passed to the method.
        feature1.printCountryReport(countries);
    }

    /**
     * Test 3: This tests what happens if the list has a missing (null) country inside it.
     * The program should skip the missing country and not crash.
     */
    @Test
    void testPrintCountryReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 6 countries. Skip the null element safely. Total Listed: 6.");
        System.out.println("Actual Result  :");

        // A new list is created by copying the valid data, then a null element is added to test the safety check.
        List<Country> countriesWithNull = new ArrayList<>(validCountries);
        countriesWithNull.add(null);

        // This mixed list is passed to the method to verify it does not crash.
        feature1.printCountryReport(countriesWithNull);
    }

    /**
     * Test 4: This tests what happens under normal conditions with good data.
     * The program should print a nice table with all the provided countries.
     */
    @Test
    void testPrintCountryReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 6 countries. Total Listed: 6.");
        System.out.println("Actual Result  :");

        // The reusable valid list is passed directly to the method.
        feature1.printCountryReport(validCountries);
    }
}