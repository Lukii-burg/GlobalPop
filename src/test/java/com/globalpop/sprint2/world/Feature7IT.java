package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class handles Integration Testing for Feature 7.
 * It interacts with the live Docker MySQL database to verify the SQL queries return correct real-world data.
 */
public class Feature7IT {

    static Feature7 feature7;

    /**
     * Initializes the Feature7 object before running tests.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 7               ");
        System.out.println("******************************************************************");

        feature7 = new Feature7();
    }

    /**
     * Tests that the SQL query successfully fetches the cities and orders them correctly.
     * Requires an active database connection via DatabaseConnection.
     */
    @Test
    void testGetCitiesByPopulationDescending() {
        // Run the method that executes the real SQL query against the database
        List<City> cities = feature7.getCitiesByPopulationDescending();

        // 1. Verify the database successfully returned a valid list
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city.");

        // 2. Verify the SQL ORDER BY is working by checking the top populated city.
        // According to the standard MySQL 'world' database, this must be Mumbai (Bombay).
        City topCity = cities.get(0);

        assertEquals("Mumbai (Bombay)", topCity.getCityName(), "Top city name should be Mumbai (Bombay)");
        assertEquals("India", topCity.getCountry(), "Top city country should be India");
        assertTrue(topCity.getPopulation() > 10000000, "Mumbai's population should be over 10 million");

        // 3. Mathematical proof that the list is descending: City 1 must be >= City 2
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation(),
                "The list must be ordered from largest population to smallest.");

        System.out.println("Integration Test Passed: Successfully retrieved and sorted " + cities.size() + " cities from the database!");
    }
}