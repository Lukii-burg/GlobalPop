package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs integration tests for Feature 20.
 * It connects to the live MySQL database in Docker to check if the SQL query
 * finds the top N most populated capital cities in the world.
 */
public class Feature20IT {

    // Stores the Feature20 object used to run the database methods during testing
    static Feature20 feature20;

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console and creates a new Feature20 instance.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 20              ");
        System.out.println("******************************************************************");

        // Create the Feature20 object so the test method can call its database functions
        feature20 = new Feature20();
    }

    /**
     * Tests if the SQL query gets the top N capital cities, joins country data, and limits the row count.
     * Needs an active database connection through DatabaseConnection to pass.
     */
    @Test
    void testGetTopNCapitalCitiesInWorld() {
        // Step 1 (Arrange): Set the limit to 5 so the query asks for the top 5 capital cities
        int limit = 5;

        // Step 2 (Act): Run the real SQL query against the live database using the limit
        List<CapitalCity> capitalCities = feature20.getTopNCapitalCitiesInWorld(limit);

        // Step 3 (Assert - List Check): Confirm the returned list is not null and contains records
        assertNotNull(capitalCities, "The returned list of capital cities should not be null.");
        assertFalse(capitalCities.isEmpty(), "The database should return at least one capital city.");

        // Step 4 (Assert - Limit Check): Confirm the list size is exactly 5.
        // This proves the SQL LIMIT clause restricted the number of rows correctly.
        assertEquals(limit, capitalCities.size(), "The SQL query should return exactly " + limit + " capital cities based on the LIMIT clause.");

        // Step 5 (Assert - Top Record Check): Get the first capital city at index 0.
        // In the world database, Seoul is the most populated capital city.
        CapitalCity topCapital = capitalCities.get(0);

        // Verify that the first record matches Seoul in South Korea
        assertEquals("Seoul", topCapital.getCapitalCityName(), "Top capital city name should be Seoul");
        assertEquals("South Korea", topCapital.getCountry(), "Top capital city country should be South Korea");
        assertTrue(topCapital.getPopulation() > 8000000, "Seoul's population should be over 8 million");

        // Step 6 (Assert - Order Check): Check that the first city population is greater than or equal to the second city.
        // This proves the results are ordered from largest population to smallest.
        assertTrue(capitalCities.get(0).getPopulation() >= capitalCities.get(1).getPopulation(),
                "The list must be ordered from largest population to smallest.");

        // Step 7 (Output): Print the formatted capital city report table to the console
        System.out.println("\n--- DATABASE RESULTS ---");
        feature20.printCapitalCityReport(capitalCities);

        // Print a final confirmation message showing the number of capital cities returned
        System.out.println("\nIntegration Test Passed: Successfully retrieved and sorted the top " + capitalCities.size() + " capital cities in the world from the database!");

    }
}