package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for Feature 6:
 * Displays the top N populated countries in a specified region.
 * Ensures displayReport handles null, empty, and valid data safely,
 * and validates input arguments for getTopNCountriesInRegion.
 */
public class Feature6Test {

    static Feature6 feature6;
    static List<Country> validCountries;
    static final String TEST_REGION = "Southeast Asia";
    static final int TEST_LIMIT = 5;

    private static Country createCountry(String code, String name, String continent, String region, long population, String capital) {
        Country c = new Country();
        c.setCode(code);
        c.setName(name);
        c.setContinent(continent);
        c.setRegion(region);
        c.setPopulation(population);
        c.setCapital(capital);
        return c;
    }

    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR FEATURE 6                 ");
        System.out.println("******************************************************************");

        feature6 = new Feature6();

        validCountries = new ArrayList<>(Arrays.asList(
                createCountry("IDN", "Indonesia", "Asia", "Southeast Asia", 212107000L, "Jakarta"),
                createCountry("VNM", "Vietnam", "Asia", "Southeast Asia", 79832000L, "Hanoi"),
                createCountry("PHL", "Philippines", "Asia", "Southeast Asia", 75967000L, "Manila"),
                createCountry("THA", "Thailand", "Asia", "Southeast Asia", 61399000L, "Bangkok"),
                createCountry("MMR", "Myanmar", "Asia", "Southeast Asia", 45611000L, "Rangoon (Yangon)")
        ));
    }

    @Test
    void testDisplayReportNull() {
        System.out.println("\n--- TEST 1: NULL INPUT ---");
        System.out.println("Expected Result: Print 'No countries found for region: \"Southeast Asia\"'");
        System.out.println("Actual Result  :");

        feature6.displayReport(null, TEST_REGION, TEST_LIMIT);
    }

    @Test
    void testDisplayReportEmpty() {
        System.out.println("\n--- TEST 2: EMPTY LIST INPUT ---");
        System.out.println("Expected Result: Print 'No countries found for region: \"Southeast Asia\"'");
        System.out.println("Actual Result  :");

        List<Country> emptyList = new ArrayList<>();
        feature6.displayReport(emptyList, TEST_REGION, TEST_LIMIT);
    }

    @Test
    void testDisplayReportContainsNull() {
        System.out.println("\n--- TEST 3: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Print table with 5 countries. Skip the null element safely.");
        System.out.println("Actual Result  :");

        List<Country> countriesWithNull = new ArrayList<>(validCountries);
        countriesWithNull.add(null);

        feature6.displayReport(countriesWithNull, TEST_REGION, TEST_LIMIT);
    }

    @Test
    void testDisplayReportValid() {
        System.out.println("\n--- TEST 4: VALID POPULATED LIST ---");
        System.out.println("Expected Result: Print table with 5 countries in Southeast Asia.");
        System.out.println("Actual Result  :");

        feature6.displayReport(validCountries, TEST_REGION, TEST_LIMIT);
    }

    @Test
    void testInvalidInputsThrowException() {
        System.out.println("\n--- TEST 5: INVALID ARGUMENTS ---");
        assertThrows(IllegalArgumentException.class, () -> feature6.getTopNCountriesInRegion("", 5));
        assertThrows(IllegalArgumentException.class, () -> feature6.getTopNCountriesInRegion(null, 5));
        assertThrows(IllegalArgumentException.class, () -> feature6.getTopNCountriesInRegion("Southeast Asia", 0));
        assertThrows(IllegalArgumentException.class, () -> feature6.getTopNCountriesInRegion("Southeast Asia", -3));
    }
}