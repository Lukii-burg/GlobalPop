package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.model.GeneralPopulationLookups;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 30.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns the total city population for a given district and country.
 */
public class Feature30IT {

    static Feature30 feature30;

    /**
     * Sets up the Feature30 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 30              ");
        System.out.println("******************************************************************");

        feature30 = new Feature30();
    }

    /**
     * Tests that the database returns the correct population total for California, United States.
     */
    @Test
    void testGetDistrictPopulation() throws SQLException {
        // Arrange: Pick a district and country to test
        String district = "California";
        String country = "United States";

        // Act: Run the SQL query against the database
        GeneralPopulationLookups result = feature30.getDistrictPopulation(district, country);

        // Assert 1: Make sure we got a valid result back
        assertNotNull(result, "The returned district population object should not be null.");

        // Assert 2: Check the formatted district name and population
        assertEquals("California (United States)", result.getName(),
                "District name should match 'California (United States)'.");
        assertTrue(result.getPopulation() > 10_000_000L,
                "Total recorded city population in California should be over 10 million.");

        System.out.println("Integration Test Passed: Successfully retrieved population for "
                + result.getName() + " (" + result.getPopulation() + ")!");
    }
}