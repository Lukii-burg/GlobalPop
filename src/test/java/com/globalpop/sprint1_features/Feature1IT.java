package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs integration tests for Feature 1.
 * It connects to the live MySQL database in Docker to check if the SQL query
 * gets all countries in the world and sorts them from largest population to smallest.
 */
public class Feature1IT {

    // Stores the Feature1 object used to run the database methods during testing
    static Feature1 feature1;

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console and creates a new Feature1 instance.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 1               ");
        System.out.println("******************************************************************");

        // Create the Feature1 object so the test method can call its database functions
        feature1 = new Feature1();
    }

    /**
     * Tests if the SQL query gets all countries from the live database and sorts them properly.
     * Needs an active database connection through DatabaseConnection to pass.
     */
    @Test
    void testGetCountriesByPopulationDescending() {
        // Step 1 (Act): Call the database method to run the real SQL query and get all countries
        List<Country> countries = feature1.getCountriesByPopulationDescending();

        // Step 2 (Assert - List Check): Check that the returned list is not null and not empty.
        // This proves the database connection opened and the query returned real rows.
        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The database should return at least one country.");

        // Step 3 (Assert - Sorting and Data Check): Get the first country in the list (index 0).
        // Because the SQL query sorts by population from highest to lowest, the first record must be China.
        Country topCountry = countries.get(0);

        // Verify that the top country details match China in the world database
        assertEquals("CHN", topCountry.getCode(), "Top country code should be CHN");
        assertEquals("China", topCountry.getName(), "Top country name should be China");
        assertEquals("Asia", topCountry.getContinent(), "Top country continent should be Asia");
        assertTrue(topCountry.getPopulation() > 1000000000L, "China's population should be over 1 billion");

        // Step 4 (Output): Print the full formatted table to the console for visual inspection
        System.out.println("\n--- DATABASE RESULTS ---");
        feature1.printCountryReport(countries);

        // Print a final confirmation message showing the total number of countries fetched
        System.out.println("\nIntegration Test Passed: Successfully retrieved and sorted " + countries.size() + " countries from the database!");
    }
}