package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 16.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns the top N populated cities in a district, sorted from largest to smallest.
 */
public class Feature16IT {

    static Feature16 feature16;

    /**
     * Sets up the Feature16 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 16              ");
        System.out.println("******************************************************************");

        feature16 = new Feature16();
    }

    /**
     * Tests that the database returns the exact number of top cities in a district,
     * sorted from largest to smallest population.
     */
    @Test
    void testGetTopCitiesInDistrict() {
        // Arrange: Ask for the top 5 cities in California
        String district = "California";
        int limit = 5;

        // Act: Run the SQL query against the database
        List<City> cities = feature16.getTopCitiesInDistrict(district, limit);

        // Assert 1: Make sure we got a valid, non-empty list back
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city for " + district);

        // Assert 2: Make sure the SQL LIMIT worked and returned exactly 5 cities
        assertEquals(limit, cities.size(), "The SQL query should return exactly " + limit + " cities.");

        // Assert 3: Check the #1 most populated city in California (Los Angeles in MySQL 'world' DB)
        City topCity = cities.get(0);
        assertEquals("Los Angeles", topCity.getCityName(), "Top city in California should be Los Angeles.");
        assertEquals("United States", topCity.getCountry(), "Country should be United States.");
        assertTrue(topCity.getPopulation() > 3_000_000, "Los Angeles population should be over 3 million.");

        // Assert 4: Make sure every city is sorted from largest to smallest population
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation(),
                    "Cities must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted top "
                + cities.size() + " cities in " + district + "!");
    }
}