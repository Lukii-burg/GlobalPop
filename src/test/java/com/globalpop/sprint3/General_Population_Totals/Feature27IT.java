package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class handles Integration Testing for Feature 27.
 * It interacts with the live Docker MySQL database to verify the SQL sum query returns the correct continent population.
 */
public class Feature27IT {

    static Feature27 feature27;

    /**
     * Initializes the Feature27 object before running tests.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 27              ");
        System.out.println("******************************************************************");

        feature27 = new Feature27();
    }

    /**
     * Tests that the SQL query successfully calculates the sum of populations for a specific continent.
     * Requires an active database connection via DatabaseConnection.
     */
    @Test
    void testGetContinentPopulation() {
        // Arrange: We will test the calculation using the continent of Asia
        String targetContinent = "Asia";

        // Act: Run the method that executes the real SQL query against the database
        long continentPopulation = feature27.getContinentPopulation(targetContinent);

        // Assert 1: Verify the database returned a positive number (preventing default 0 returns)
        assertTrue(continentPopulation > 0, "The database should return a population greater than 0 for " + targetContinent + ".");

        // Assert 2: Verify the SQL SUM and CAST(SIGNED) successfully handled the large dataset for the continent.
        // The standard MySQL 'world' database population for Asia is roughly 3.7 billion.
        assertTrue(continentPopulation > 3000000000L,
                "The population of Asia should be successfully calculated as over 3 billion without integer overflow.");

        System.out.println("Integration Test Passed: Successfully calculated the population of " + targetContinent + " from the database as " + String.format("%,d", continentPopulation) + "!");
    }
}