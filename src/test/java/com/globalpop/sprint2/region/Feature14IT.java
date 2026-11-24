package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class handles Integration Testing for Feature 14.
 * It interacts with the live Docker MySQL database to verify the SQL queries return correct real-world data.
 */
public class Feature14IT {

    static Feature14 feature14;

    /**
     * Initializes the Feature14 object before running tests.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 14              ");
        System.out.println("******************************************************************");

        feature14 = new Feature14();
    }

    /**
     * Tests that the SQL query successfully fetches the cities, filters by region, and applies the LIMIT.
     * Requires an active database connection via DatabaseConnection.
     */
    @Test
    void testGetTopNCitiesInRegion() {
        // Arrange: We want the top 5 cities in Southeast Asia
        String targetRegion = "Southeast Asia";
        int limit = 5;

        // Act: Run the method that executes the real SQL query against the database
        List<City> cities = feature14.getTopNCitiesInRegion(targetRegion, limit);

        // Assert 1: Verify the database successfully returned a valid list
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city.");

        // Assert 2: Verify the SQL LIMIT clause worked correctly
        assertEquals(limit, cities.size(), "The SQL query should return exactly exactly " + limit + " cities based on the LIMIT clause.");

        // Assert 3: Verify the SQL ordering and filtering is working.
        // According to the standard MySQL 'world' database, the #1 city in Southeast Asia is Jakarta.
        City topCity = cities.get(0);

        assertEquals("Jakarta", topCity.getCityName(), "Top city name should be Jakarta");
        assertEquals("Indonesia", topCity.getCountry(), "Top city country should be Indonesia");

        // Assert 4: Verify the descending order logic (City 1 should be >= City 2)
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation(),
                "The list must be ordered from largest population to smallest.");

        System.out.println("Integration Test Passed: Successfully retrieved and sorted the top " + cities.size() + " cities in " + targetRegion + " from the database!");
    }
}