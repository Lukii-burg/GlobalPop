package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class handles Integration Testing for Feature 1.
 * It interacts with the live Docker MySQL database to verify the SQL queries return correct real-world data.
 */
public class Feature1IT {

    static Feature1 feature1;

    /**
     * Initializes the Feature1 object before running tests.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 1               ");
        System.out.println("******************************************************************");

        feature1 = new Feature1();
    }

    /**
     * Tests that the SQL query successfully fetches the countries and orders them correctly.
     * Requires an active database connection via DatabaseConnection.
     */
    @Test
    void testGetCountriesByPopulationDescending() {
        // Run the method that executes the real SQL query against the database
        List<Country> countries = feature1.getCountriesByPopulationDescending();

        // 1. Verify the database successfully returned a valid list
        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The database should return at least one country.");

        // 2. Verify the SQL ORDER BY is working by checking the top populated country.
        // According to the standard MySQL 'world' database, this must be China.
        Country topCountry = countries.get(0);

        assertEquals("CHN", topCountry.getCode(), "Top country code should be CHN");
        assertEquals("China", topCountry.getName(), "Top country name should be China");
        assertEquals("Asia", topCountry.getContinent(), "Top country continent should be Asia");
        assertTrue(topCountry.getPopulation() > 1000000000L, "China's population should be over 1 billion");

        System.out.println("Integration Test Passed: Successfully retrieved and sorted " + countries.size() + " countries from the database!");
    }
}