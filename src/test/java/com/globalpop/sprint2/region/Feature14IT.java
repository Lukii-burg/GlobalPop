package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs integration tests for Feature 14.
 * It connects to the live MySQL database in Docker to check if the SQL query
 * filters cities by a chosen region and returns the top N most populated cities.
 */
public class Feature14IT {

    // Stores the Feature14 object used to run the database methods during testing
    static Feature14 feature14;

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console and creates a new Feature14 instance.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 14              ");
        System.out.println("******************************************************************");

        // Create the Feature14 object so the test method can call its database functions
        feature14 = new Feature14();
    }

    /**
     * Tests if the SQL query filters cities by region, sorts them by population, and limits the output to N rows.
     * Needs an active database connection through DatabaseConnection to pass.
     */
    @Test
    void testGetTopNCitiesInRegion() {
        // Step 1 (Arrange): Set the region filter to "Southeast Asia" and set the maximum row limit to 5
        String targetRegion = "Southeast Asia";
        int limit = 5;

        // Step 2 (Act): Call the database method to run the real SQL query with the region and limit values
        List<City> cities = feature14.getTopNCitiesInRegion(targetRegion, limit);

        // Step 3 (Assert - List Check): Verify the returned list is not null and contains at least one city
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city.");

        // Step 4 (Assert - Limit Check): Verify the list contains exactly 5 cities.
        // This confirms the SQL LIMIT clause works properly.
        assertEquals(limit, cities.size(), "The SQL query should return exactly " + limit + " cities based on the LIMIT clause.");

        // Step 5 (Assert - Filter and Top Record Check): Get the first city in the list (index 0).
        // In the Southeast Asia region of the world database, Jakarta has the highest population.
        City topCity = cities.get(0);

        // Confirm the top city is Jakarta in Indonesia
        assertEquals("Jakarta", topCity.getCityName(), "Top city name should be Jakarta");
        assertEquals("Indonesia", topCity.getCountry(), "Top city country should be Indonesia");

        // Step 6 (Assert - Order Check): Check that the first city has a population greater than or equal to the second city.
        // This proves the list is sorted from largest to smallest.
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation(),
                "The list must be ordered from largest population to smallest.");

        // Step 7 (Output): Print the formatted city table to the console for visual verification
        System.out.println("\n--- DATABASE RESULTS ---");
        feature14.printCityReport(cities);

        // Print a final confirmation message with the region name and city count
        System.out.println("\nIntegration Test Passed: Successfully retrieved and sorted the top " + cities.size() + " cities in " + targetRegion + " from the database!");
    }
}