package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 22.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns the top N populated capital cities in a region, sorted from largest to smallest.
 */
public class Feature22IT {

    static Feature22 feature22;

    /**
     * Sets up the Feature22 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 22              ");
        System.out.println("******************************************************************");

        feature22 = new Feature22();
    }

    /**
     * Tests that the database returns the exact number of top capital cities in a region,
     * sorted from largest to smallest population.
     */
    @Test
    void testGetTopNCapitalCitiesInRegion() {
        // Arrange: Ask for the top 5 capital cities in Southeast Asia
        String region = "Southeast Asia";
        int limit = 5;

        // Act: Run the SQL query against the database
        List<CapitalCity> capitalCities = feature22.getTopNCapitalCitiesInRegion(region, limit);

        // Assert 1: Make sure we got a valid, non-empty list back
        assertNotNull(capitalCities, "The returned list of capital cities should not be null.");
        assertFalse(capitalCities.isEmpty(), "The database should return at least one capital city for " + region);

        // Assert 2: Make sure the SQL LIMIT worked and returned exactly 5 capital cities
        assertEquals(limit, capitalCities.size(), "The SQL query should return exactly " + limit + " capital cities.");

        // Assert 3: Check the #1 most populated capital city in Southeast Asia (Jakarta in MySQL 'world' DB)
        CapitalCity topCapital = capitalCities.get(0);
        assertEquals("Jakarta", topCapital.getCapitalCityName(), "Top capital city in Southeast Asia should be Jakarta.");
        assertEquals("Indonesia", topCapital.getCountry(), "Country should be Indonesia.");
        assertTrue(topCapital.getPopulation() > 9_000_000, "Jakarta's population should be over 9 million.");

        // Assert 4: Make sure every capital city is sorted from largest to smallest population
        for (int i = 0; i < capitalCities.size() - 1; i++) {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i + 1).getPopulation(),
                    "Capital cities must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted top "
                + capitalCities.size() + " capital cities in " + region + "!");
    }
}