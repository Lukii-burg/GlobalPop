package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 13.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns the top N populated cities in a continent, sorted from largest to smallest.
 */
public class Feature13IT {

    static Feature13 feature13;

    /**
     * Sets up the Feature13 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 13              ");
        System.out.println("******************************************************************");

        feature13 = new Feature13();
    }

    /**
     * Tests that the database returns the exact number of top cities in Asia,
     * sorted from largest to smallest population.
     */
    @Test
    void testGetTopCitiesInContinent() throws SQLException {
        // Arrange: Ask for the top 5 cities in Asia
        String continent = "Asia";
        int limit = 5;

        // Act: Run the SQL query against the database
        List<City> cities = feature13.getTopCitiesInContinent(continent, limit);

        // Assert 1: Make sure we got a valid, non-empty list back
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city for " + continent);

        // Assert 2: Make sure the SQL LIMIT worked and returned exactly 5 cities
        assertEquals(limit, cities.size(), "The SQL query should return exactly " + limit + " cities.");

        // Assert 3: Check the #1 most populated city in Asia (Mumbai in MySQL 'world' DB)
        City topCity = cities.get(0);
        assertEquals("Mumbai (Bombay)", topCity.getCityName(), "Top city in Asia should be Mumbai (Bombay).");
        assertEquals("India", topCity.getCountry(), "Country should be India.");
        assertTrue(topCity.getPopulation() > 10_000_000, "Top city population should be over 10 million.");

        // Assert 4: Make sure every city is sorted from largest to smallest population
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation(),
                    "Cities must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted top "
                + cities.size() + " cities in " + continent + "!");
    }
}