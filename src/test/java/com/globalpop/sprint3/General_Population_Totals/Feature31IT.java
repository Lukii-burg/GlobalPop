package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.model.GeneralPopulationLookups;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 31.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns the population for a given city and country.
 */
public class Feature31IT {

    static Feature31 feature31;

    /**
     * Sets up the Feature31 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 31              ");
        System.out.println("******************************************************************");

        feature31 = new Feature31();
    }

    /**
     * Tests that the database returns the correct population for Los Angeles, United States.
     */
    @Test
    void testGetCityPopulation() throws SQLException {
        // Arrange: Pick a city and country to test
        String city = "Los Angeles";
        String country = "United States";

        // Act: Run the SQL query against the database
        GeneralPopulationLookups result = feature31.getCityPopulation(city, country);

        // Assert 1: Make sure we got a valid result back
        assertNotNull(result, "The returned city population object should not be null.");

        // Assert 2: Check the formatted city name and population
        assertEquals("Los Angeles (United States)", result.getName(),
                "City name should match 'Los Angeles (United States)'.");
        assertTrue(result.getPopulation() > 3_000_000L,
                "Los Angeles population should be over 3 million.");

        System.out.println("Integration Test Passed: Successfully retrieved population for "
                + result.getName() + " (" + result.getPopulation() + ")!");
    }
}