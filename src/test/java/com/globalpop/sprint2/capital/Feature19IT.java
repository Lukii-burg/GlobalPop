package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 19.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns all capital cities in a region, sorted from largest to smallest population.
 */
public class Feature19IT {

    static Feature19 feature19;

    /**
     * Sets up the Feature19 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 19              ");
        System.out.println("******************************************************************");

        feature19 = new Feature19();
    }

    /**
     * Tests that the database returns all capital cities in Southeast Asia,
     * sorted from largest to smallest population.
     */
    @Test
    void testGetCapitalCitiesInRegion() throws SQLException {
        // Arrange: Pick a region to test
        String region = "Southeast Asia";

        // Act: Run the SQL query against the database
        List<CapitalCity> capitalCities = feature19.getCapitalCitiesInRegion(region);

        // Assert 1: Make sure we got a valid, non-empty list back
        assertNotNull(capitalCities, "The returned list of capital cities should not be null.");
        assertFalse(capitalCities.isEmpty(), "The database should return at least one capital city for " + region);

        // Assert 2: Check the #1 most populated capital city in Southeast Asia (Jakarta in MySQL 'world' DB)
        CapitalCity topCapital = capitalCities.get(0);
        assertEquals("Jakarta", topCapital.getCapitalCityName(), "Top capital city in Southeast Asia should be Jakarta.");
        assertEquals("Indonesia", topCapital.getCountry(), "Country should be Indonesia.");
        assertTrue(topCapital.getPopulation() > 9_000_000, "Jakarta's population should be over 9 million.");

        // Assert 3: Make sure every capital city is sorted from largest to smallest population
        for (int i = 0; i < capitalCities.size() - 1; i++) {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i + 1).getPopulation(),
                    "Capital cities must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted "
                + capitalCities.size() + " capital cities in " + region + "!");
    }
}