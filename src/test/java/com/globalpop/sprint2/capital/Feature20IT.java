package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class handles Integration Testing for Feature 20.
 * It interacts with the live Docker MySQL database to verify the SQL queries return correct real-world data.
 */
public class Feature20IT {

    static Feature20 feature20;

    /**
     * Initializes the Feature20 object before running tests.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 20              ");
        System.out.println("******************************************************************");

        feature20 = new Feature20();
    }

    /**
     * Tests that the SQL query successfully fetches the capital cities and applies the LIMIT correctly.
     * Requires an active database connection via DatabaseConnection.
     */
    @Test
    void testGetTopNCapitalCitiesInWorld() {
        // Arrange: We want the top 5 capital cities globally
        int limit = 5;

        // Act: Run the method that executes the real SQL query against the database
        List<CapitalCity> capitalCities = feature20.getTopNCapitalCitiesInWorld(limit);

        // Assert 1: Verify the database successfully returned a valid list
        assertNotNull(capitalCities, "The returned list of capital cities should not be null.");
        assertFalse(capitalCities.isEmpty(), "The database should return at least one capital city.");

        // Assert 2: Verify the SQL LIMIT clause worked correctly
        assertEquals(limit, capitalCities.size(), "The SQL query should return exactly " + limit + " capital cities based on the LIMIT clause.");

        // Assert 3: Verify the SQL ordering is working.
        // According to the standard MySQL 'world' database, the #1 populated capital city is Seoul.
        CapitalCity topCapital = capitalCities.get(0);

        assertEquals("Seoul", topCapital.getCapitalCityName(), "Top capital city name should be Seoul");
        assertEquals("South Korea", topCapital.getCountry(), "Top capital city country should be South Korea");
        assertTrue(topCapital.getPopulation() > 8000000, "Seoul's population should be over 8 million");

        // Assert 4: Verify the descending order logic (City 1 should be >= City 2)
        assertTrue(capitalCities.get(0).getPopulation() >= capitalCities.get(1).getPopulation(),
                "The list must be ordered from largest population to smallest.");

        System.out.println("Integration Test Passed: Successfully retrieved and sorted the top " + capitalCities.size() + " capital cities in the world from the database!");
    }
}