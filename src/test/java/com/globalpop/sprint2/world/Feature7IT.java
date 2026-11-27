package com.globalpop.sprint2.world;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs integration tests for Feature 7.
 * It connects to the live MySQL database in Docker to check if the SQL query
 * gets all cities in the world and sorts them from largest population to smallest.
 */
public class Feature7IT {

    // Stores the Feature7 object used to run the database methods during testing
    static Feature7 feature7;

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console and creates a new Feature7 instance.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 7               ");
        System.out.println("******************************************************************");

        // Create the Feature7 object so the test method can call its database functions
        feature7 = new Feature7();
    }

    /**
     * Tests if the SQL query fetches all cities from the live database and orders them by population descending.
     * Needs an active database connection through DatabaseConnection to pass.
     */
    @Test
    void testGetCitiesByPopulationDescending() {
        // Step 1 (Act): Call the database method to run the real SQL query and fetch all cities
        List<City> cities = feature7.getCitiesByPopulationDescending();

        // Step 2 (Assert - List Check): Verify the returned list is not null and contains city records
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The database should return at least one city.");

        // Step 3 (Assert - Top Record Check): Get the first city in the list (index 0).
        // In the world database, Mumbai (Bombay) has the largest city population.
        City topCity = cities.get(0);

        // Confirm the top city details match Mumbai (Bombay) in India
        assertEquals("Mumbai (Bombay)", topCity.getCityName(), "Top city name should be Mumbai (Bombay)");
        assertEquals("India", topCity.getCountry(), "Top city country should be India");
        assertTrue(topCity.getPopulation() > 10000000, "Mumbai's population should be over 10 million");

        // Step 4 (Assert - Order Check): Check that the first city population is greater than or equal to the second city.
        // This proves the SQL ORDER BY clause sorted all rows from largest to smallest.
        assertTrue(cities.get(0).getPopulation() >= cities.get(1).getPopulation(),
                "The list must be ordered from largest population to smallest.");

        // Step 5 (Output): Print the formatted table of all cities to the console
        System.out.println("\n--- DATABASE RESULTS ---");
        feature7.printCityReport(cities);

        // Print a final confirmation message showing the total number of cities retrieved
        System.out.println("\nIntegration Test Passed: Successfully retrieved and sorted " + cities.size() + " cities from the database!");
    }
}