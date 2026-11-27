package com.globalpop.sprint1_features;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for Feature 3:
 * Displays all countries in a specified region, ordered by population (largest to smallest).
 * Ensures displayReport handles null, empty, and valid data safely without crashing.
 */
public class Feature3Test {

    static List<Feature3.Country> validCountries;
    static final String TEST_REGION = "Southeast Asia";

    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 3                 ");
        System.out.println("******************************************************************");

        validCountries = new ArrayList<>(Arrays.asList(
                new Feature3.Country("IDN", "Indonesia", "Asia", "Southeast Asia", 212107000L, "Jakarta"),
                new Feature3.Country("VNM", "Vietnam", "Asia", "Southeast Asia", 79832000L, "Hanoi"),
                new Feature3.Country("PHL", "Philippines", "Asia", "Southeast Asia", 75967000L, "Manila"),
                new Feature3.Country("THA", "Thailand", "Asia", "Southeast Asia", 61399000L, "Bangkok"),
                new Feature3.Country("MMR", "Myanmar", "Asia", "Southeast Asia", 45611000L, "Rangoon (Yangon)")
        ));
    }

    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No records found for the specified region.'");
        System.out.println("Actual Result  :");

        Feature3.displayReport(TEST_REGION, null);
    }

    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No records found for the specified region.'");
        System.out.println("Actual Result  :");

        List<Feature3.Country> emptyList = new ArrayList<>();
        Feature3.displayReport(TEST_REGION, emptyList);
    }

    @Test
    void testDisplayReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 countries. Skip the null element safely.");
        System.out.println("Actual Result  :");

        List<Feature3.Country> countriesWithNull = new ArrayList<>(validCountries);
        countriesWithNull.add(null);

        Feature3.displayReport(TEST_REGION, countriesWithNull);
    }

    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 countries in Southeast Asia.");
        System.out.println("Actual Result  :");

        Feature3.displayReport(TEST_REGION, validCountries);
    }
}