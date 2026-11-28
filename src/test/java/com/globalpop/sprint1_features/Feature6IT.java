package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 6.
 * Interacts with the live Docker MySQL database to verify the SQL query returns
 * the top N populated countries in a region, sorted descending.
 */
public class Feature6IT {

    static Feature6 feature6;

    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 6               ");
        System.out.println("******************************************************************");

        feature6 = new Feature6();
    }

    @Test
    void testGetTopNCountriesInRegion() throws SQLException {
        // Arrange
        String region = "Southeast Asia";
        int limit = 5;

        // Act
        List<Country> countries = feature6.getTopNCountriesInRegion(region, limit);

        // Assert 1: Verify the database returned a valid non-empty list
        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The database should return at least one country for " + region);

        // Assert 2: Verify the SQL LIMIT clause worked correctly
        assertEquals(limit, countries.size(), "The SQL query should return exactly " + limit + " countries.");

        // Assert 3: Verify the top populated country in Southeast Asia (Indonesia in MySQL 'world' DB)
        Country topCountry = countries.get(0);
        assertEquals("Indonesia", topCountry.getName(), "Most populated country in Southeast Asia should be Indonesia.");
        assertEquals("Southeast Asia", topCountry.getRegion(), "Region should match Southeast Asia.");
        assertTrue(topCountry.getPopulation() > 200_000_000L, "Indonesia's population should be over 200 million.");

        // Assert 4: Verify descending population order across all returned records
        for (int i = 0; i < countries.size() - 1; i++) {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation(),
                    "Countries must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted top "
                + countries.size() + " countries in " + region + "!");
    }
}