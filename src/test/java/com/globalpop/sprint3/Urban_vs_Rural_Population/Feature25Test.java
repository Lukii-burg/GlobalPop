package com.globalpop.sprint3.Urban_vs_Rural_Population;


import com.globalpop.model.PopulationLivingInVsOutOfCities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Feature 25.
 * Feature 25 shows the total population, city population, and non-city population for each country.
 * These tests make sure the program does not crash when given bad or missing data.
 */
public class Feature25Test {

    static Feature25 feature25;
    static List<PopulationLivingInVsOutOfCities> validReports;

    /**
     * Runs once before all tests to set up the Feature25 object and sample country data.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 25                ");
        System.out.println("******************************************************************");

        feature25 = new Feature25();

        // Sample test data mimicking country urban vs rural population breakdowns
        validReports = new ArrayList<>(Arrays.asList(
                new PopulationLivingInVsOutOfCities("China", 1277558000L, 175953614L, 1101604386L),
                new PopulationLivingInVsOutOfCities("India", 1013662000L, 123298526L, 890363474L),
                new PopulationLivingInVsOutOfCities("United States", 278357000L, 78625774L, 199731226L),
                new PopulationLivingInVsOutOfCities("Indonesia", 212107000L, 37485695L, 174621305L),
                new PopulationLivingInVsOutOfCities("Myanmar", 45611000L, 6226700L, 39384300L)
        ));
    }

    /**
     * Test 1: Checks what happens if the report list is null.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No country population data was found.'");
        System.out.println("Actual Result  :");

        feature25.displayReport(null);
    }

    /**
     * Test 2: Checks what happens if the report list is empty.
     * The program should print a message and not crash.
     */
    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No country population data was found.'");
        System.out.println("Actual Result  :");

        List<PopulationLivingInVsOutOfCities> emptyList = new ArrayList<>();
        feature25.displayReport(emptyList);
    }

    /**
     * Test 3: Checks what happens if there is a null item inside the list.
     * The program should skip the null item and print the rest.
     */
    @Test
    void testDisplayReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 countries and skip the null item safely.");
        System.out.println("Actual Result  :");

        List<PopulationLivingInVsOutOfCities> reportsWithNull = new ArrayList<>(validReports);
        reportsWithNull.add(null);

        feature25.displayReport(reportsWithNull);
    }

    /**
     * Test 4: Checks normal behavior with valid population data.
     * The program should print a clean table of all 5 countries with percentages.
     */
    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 countries and their city vs non-city populations.");
        System.out.println("Actual Result  :");

        feature25.displayReport(validReports);
    }
}