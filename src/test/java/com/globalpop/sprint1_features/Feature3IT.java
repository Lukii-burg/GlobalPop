package com.globalpop.sprint1_features;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 3.
 * Interacts with the live Docker MySQL database to verify the SQL query returns
 * all countries in a region sorted by population descending.
 */
public class Feature3IT {

    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 3               ");
        System.out.println("******************************************************************");
    }

    @Test
    void testFetchCountriesInRegion() {
        // Arrange
        String region = "Southeast Asia";

        // Act
        List<Feature3.Country> countries = Feature3.fetchCountriesInRegion(region);

        // Assert 1: Verify the database returned a valid non-empty list
        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The database should return at least one country for " + region);

        // Assert 2: Verify the top populated country in Southeast Asia (Indonesia in MySQL 'world' DB)
        Feature3.Country topCountry = countries.get(0);
        assertEquals("Indonesia", topCountry.getName(), "Most populated country in Southeast Asia should be Indonesia.");
        assertEquals("Southeast Asia", topCountry.getRegion(), "Region should match Southeast Asia.");
        assertTrue(topCountry.getPopulation() > 200_000_000L, "Indonesia's population should be over 200 million.");

        // Assert 3: Verify descending population order across all returned records
        for (int i = 0; i < countries.size() - 1; i++) {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation(),
                    "Countries must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted "
                + countries.size() + " countries in " + region + "!");
    }
}