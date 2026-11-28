package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 8.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns all cities in a continent, sorted from largest to smallest population.
 */
public class Feature8IT {

    static Feature8 feature8;

    /**
     * Sets up the Feature8 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 8               ");
        System.out.println("******************************************************************");

        feature8 = new Feature8();
    }

    /**
     * Tests that the database returns cities in Asia sorted by population descending.
     */
    @Test
    void testGetCitiesInContinent() {
        // Arrange: Pick a continent to test
        String continent = "Asia";

        // Act: Run the SQL query against the database
        List<City> cities = feature8.getCitiesInContinent(continent);

        // Assert 1: Make sure we got a valid, non-empty list back
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city for " + continent);

        // Assert 2: Check the #1 most populated city in Asia
        City topCity = cities.get(0);
        assertEquals("Mumbai (Bombay)", topCity.getCityName(), "Top city in Asia should be Mumbai (Bombay).");
        assertTrue(topCity.getPopulation() > 10_000_000, "Top city population should be over 10 million.");

        // Assert 3: Make sure every city is sorted from largest to smallest population
        for (int i = 0; i < cities.size() - 1; i++) {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i + 1).getPopulation(),
                    "Cities must be ordered from largest population to smallest.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved and sorted "
                + cities.size() + " cities in " + continent + "!");
    }
}