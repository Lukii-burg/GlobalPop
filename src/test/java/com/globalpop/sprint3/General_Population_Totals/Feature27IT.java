package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs integration tests for Feature 27.
 * It connects to the live MySQL database in Docker to check if the SQL SUM query
 * calculates the total population for one chosen continent.
 */
public class Feature27IT {

    // Stores the Feature27 object used to run the database methods during testing
    static Feature27 feature27;

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console and creates a new Feature27 instance.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 27              ");
        System.out.println("******************************************************************");

        // Create the Feature27 object so the test method can call its database functions
        feature27 = new Feature27();
    }

    /**
     * Tests if the SQL SUM query filters by continent and calculates the total population accurately.
     * Needs an active database connection through DatabaseConnection to pass.
     */
    @Test
    void testGetContinentPopulation() {
        // Step 1 (Arrange): Set the continent filter to "Asia"
        String targetContinent = "Asia";

        // Step 2 (Act): Call the database method to run the SQL SUM query for the chosen continent
        long continentPopulation = feature27.getContinentPopulation(targetContinent);

        // Step 3 (Assert - Positive Value Check): Confirm the database returned a value greater than 0.
        // This proves the WHERE clause matched records for Asia and returned a valid sum.
        assertTrue(continentPopulation > 0, "The database should return a population greater than 0 for " + targetContinent + ".");

        // Step 4 (Assert - Large Number Check): Confirm the population of Asia is over 3 billion.
        // This proves the long data type and SQL CAST handled the large continent sum without overflow.
        assertTrue(continentPopulation > 3000000000L,
                "The population of Asia should be successfully calculated as over 3 billion without integer overflow.");

        // Step 5 (Output): Print the formatted continent population report to the console
        System.out.println("\n--- DATABASE RESULTS ---");
        feature27.printPopulationReport(targetContinent, continentPopulation);

        // Print a final confirmation message showing the continent name and formatted total population
        System.out.println("\nIntegration Test Passed: Successfully calculated the population of " + targetContinent + " from the database as " + String.format("%,d", continentPopulation) + "!");
    }
}