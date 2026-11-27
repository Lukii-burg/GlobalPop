package com.globalpop.sprint2.capital;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature21 verifying live database connectivity,
 * JOIN query execution between country and city tables, population ordering,
 * limit behavior, and full report execution.
 */
class Feature21IT {

    private String targetContinent;
    private int defaultLimit;

    /**
     * Initializes default continent and limit parameters before each integration test runs.
     */
    @BeforeEach
    void setUp() {
        targetContinent = "Asia";
        defaultLimit = 10;
    }

    /**
     * Verifies that the live database is reachable and getTopPopulatedCapitalCities(continent, n)
     * returns CapitalCity records ordered by population descending.
     */
    @Test
    void testGetTopPopulatedCapitalCitiesFromDatabase() {
        System.out.println("\n--- TEST: GET TOP POPULATED CAPITAL CITIES FROM DB ---");
        System.out.println("Expected Result: Retrieve up to " + defaultLimit + " capital cities in '" + targetContinent
                + "' ordered by population descending from live DB.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertFalse(connection.isClosed(), "Database connection should be open.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            List<CapitalCity> capitalCities = Feature21.getTopPopulatedCapitalCities(targetContinent, defaultLimit);

            assertNotNull(capitalCities, "Returned capital city list should not be null.");
            assertFalse(capitalCities.isEmpty(), "Returned capital city list should not be empty for " + targetContinent + ".");
            assertTrue(capitalCities.size() <= defaultLimit, "Returned list size should not exceed limit " + defaultLimit + ".");

            for (int i = 0; i < capitalCities.size(); i++) {
                CapitalCity city = capitalCities.get(i);
                assertNotNull(city, "CapitalCity entry should not be null.");
                assertNotNull(city.getCapitalCityName(), "Capital city name should not be null.");
                assertFalse(city.getCapitalCityName().isBlank(), "Capital city name should not be blank.");
                assertNotNull(city.getCountry(), "Country name should not be null.");
                assertFalse(city.getCountry().isBlank(), "Country name should not be blank.");
                assertTrue(city.getPopulation() >= 0, "Population should be non-negative.");

                if (i > 0) {
                    int previousPopulation = capitalCities.get(i - 1).getPopulation();
                    assertTrue(
                            previousPopulation >= city.getPopulation(),
                            "Capital cities should be ordered from highest to lowest population."
                    );
                }
            }

            System.out.println("Retrieved " + capitalCities.size() + " capital cities in " + targetContinent
                    + ". Top capital: " + capitalCities.get(0).getCapitalCityName()
                    + " (" + capitalCities.get(0).getCountry() + ", " + capitalCities.get(0).getPopulation() + ")");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies limit boundary behavior against the live database by requesting a single top capital city.
     */
    @Test
    void testTopOneCapitalCityLimitFromDatabase() {
        System.out.println("\n--- TEST: TOP 1 CAPITAL CITY LIMIT FROM DB ---");
        System.out.println("Expected Result: Retrieve exactly 1 capital city for continent '" + targetContinent + "' when limit is 1.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            List<CapitalCity> topOne = Feature21.getTopPopulatedCapitalCities(targetContinent, 1);

            assertNotNull(topOne, "Returned list should not be null.");
            assertEquals(1, topOne.size(), "Expected exactly 1 capital city when n = 1.");
            assertNotNull(topOne.get(0).getCapitalCityName());
            assertNotNull(topOne.get(0).getCountry());

            System.out.println("Top 1 capital city in " + targetContinent + ": "
                    + topOne.get(0).getCapitalCityName() + " (" + topOne.get(0).getCountry() + ")");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that Feature21.run() executes end-to-end against the live database
     * and prints the formatted report without throwing uncaught exceptions.
     */
    @Test
    void testRunFeature21EndToEnd() {
        System.out.println("\n--- TEST: RUN FEATURE 21 END-TO-END ---");
        System.out.println("Expected Result: Execute Feature21.run() against live database and display Report 21.");
        System.out.println("Actual Result  :");

        Feature21 feature21 = new Feature21();
        assertDoesNotThrow(feature21::run, "Feature21.run() should complete without throwing uncaught exceptions.");
        System.out.println("Feature21.run() completed end-to-end.");
    }
}