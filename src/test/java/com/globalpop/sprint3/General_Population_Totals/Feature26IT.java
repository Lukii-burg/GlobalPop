package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class handles Integration Testing for Feature 26.
 * It interacts with the live Docker MySQL database to verify the SQL sum query returns the correct global population.
 */
public class Feature26IT {

    static Feature26 feature26;

    /**
     * Initializes the Feature26 object before running tests.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 26              ");
        System.out.println("******************************************************************");

        feature26 = new Feature26();
    }

    /**
     * Tests that the SQL query successfully calculates the sum of all country populations.
     * Requires an active database connection via DatabaseConnection.
     */
    @Test
    void testGetWorldPopulation() {
        // Act: Run the method that executes the real SQL query against the database
        long worldPopulation = feature26.getWorldPopulation();

        // Assert 1: Verify the database returned a positive number (preventing default 0 returns)
        assertTrue(worldPopulation > 0, "The database should return a population greater than 0.");

        // Assert 2: Verify the SQL SUM and CAST(SIGNED) successfully handled the large dataset.
        // The standard MySQL 'world' database global population sum is roughly 6.07 billion.
        assertTrue(worldPopulation > 6000000000L,
                "The world population should be successfully calculated as over 6 billion without integer overflow.");

        System.out.println("Integration Test Passed: Successfully calculated the World Population from the database as " + String.format("%,d", worldPopulation) + "!");
    }
}