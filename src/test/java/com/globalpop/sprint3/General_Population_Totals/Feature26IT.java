package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs integration tests for Feature 26.
 * It connects to the live MySQL database in Docker to check if the SQL SUM query
 * adds up the populations of all countries to get the total world population.
 */
public class Feature26IT {

    // Stores the Feature26 object used to run the database methods during testing
    static Feature26 feature26;

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console and creates a new Feature26 instance.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 26              ");
        System.out.println("******************************************************************");

        // Create the Feature26 object so the test method can call its database functions
        feature26 = new Feature26();
    }

    /**
     * Tests if the SQL SUM query calculates the total population of the world without number overflow.
     * Needs an active database connection through DatabaseConnection to pass.
     */
    @Test
    void testGetWorldPopulation() {
        // Step 1 (Act): Call the database method to run the SQL SUM query across all countries
        long worldPopulation = feature26.getWorldPopulation();

        // Step 2 (Assert - Positive Value Check): Confirm the database returned a value greater than 0.
        // This proves the query ran properly and did not return a default zero value.
        assertTrue(worldPopulation > 0, "The database should return a population greater than 0.");

        // Step 3 (Assert - Large Number Check): Confirm the world population is over 6 billion.
        // This proves the long data type and SQL CAST handled the large sum without integer overflow.
        assertTrue(worldPopulation > 6000000000L,
                "The world population should be successfully calculated as over 6 billion without integer overflow.");

        // Step 4 (Output): Print the formatted world population report to the console
        System.out.println("\n--- DATABASE RESULTS ---");
        feature26.printPopulationReport(worldPopulation);

        // Print a final confirmation message showing the calculated world population with commas
        System.out.println("\nIntegration Test Passed: Successfully calculated the World Population from the database as " + String.format("%,d", worldPopulation) + "!");
    }
}