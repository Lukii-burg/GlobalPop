package com.globalpop.sprint2.region;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature15 verifying live database connectivity,
 * country-based JOIN query execution between city and country tables,
 * case-insensitive country code handling, population ordering, limit behavior,
 * and end-to-end report execution.
 */
class Feature15IT {

    private String targetCountryCode;
    private int defaultLimit;

    /**
     * Initializes default country code and limit parameters before each integration test runs.
     */
    @BeforeEach
    void setUp() {
        targetCountryCode = "GBR";
        defaultLimit = 10;
    }

    /**
     * Verifies that the live database is reachable and getTopPopulatedCities(countryCode, n)
     * returns City records for the selected country ordered by population descending.
     */
    @Test
    void testGetTopPopulatedCitiesFromDatabase() {
        System.out.println("\n--- TEST: GET TOP POPULATED CITIES FROM DB ---");
        System.out.println("Expected Result: Retrieve up to " + defaultLimit + " cities for country code '"
                + targetCountryCode + "' ordered by population descending from live DB.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertFalse(connection.isClosed(), "Database connection should be open.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            List<City> cities = Feature15.getTopPopulatedCities(targetCountryCode, defaultLimit);

            assertNotNull(cities, "Returned city list should not be null.");
            assertFalse(cities.isEmpty(), "Returned city list should not be empty for " + targetCountryCode + ".");
            assertTrue(cities.size() <= defaultLimit, "Returned list size should not exceed limit " + defaultLimit + ".");

            for (int i = 0; i < cities.size(); i++) {
                City city = cities.get(i);
                assertNotNull(city, "City entry should not be null.");
                assertNotNull(city.getCityName(), "City name should not be null.");
                assertFalse(city.getCityName().isBlank(), "City name should not be blank.");
                assertNotNull(city.getCountry(), "Country name should not be null.");
                assertFalse(city.getCountry().isBlank(), "Country name should not be blank.");
                assertTrue(city.getPopulation() >= 0, "Population should be non-negative.");

                if (i > 0) {
                    int previousPopulation = cities.get(i - 1).getPopulation();
                    assertTrue(
                            previousPopulation >= city.getPopulation(),
                            "Cities should be ordered from highest to lowest population."
                    );
                }
            }

            System.out.println("Retrieved " + cities.size() + " cities for " + targetCountryCode
                    + ". Top city: " + cities.get(0).getCityName()
                    + " (" + cities.get(0).getCountry() + ", " + cities.get(0).getPopulation() + ")");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that lowercase country codes are normalized to uppercase against the live database
     * and return matching results for a limit of 1.
     */
    @Test
    void testLowercaseCountryCodeAndSingleLimitFromDatabase() {
        System.out.println("\n--- TEST: LOWERCASE COUNTRY CODE AND LIMIT 1 FROM DB ---");
        System.out.println("Expected Result: Normalize lowercase 'gbr' to 'GBR' and retrieve 1 top city from live DB.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            List<City> topCity = Feature15.getTopPopulatedCities("gbr", 1);

            assertNotNull(topCity, "Returned list should not be null.");
            assertEquals(1, topCity.size(), "Expected exactly 1 city when n = 1.");
            assertNotNull(topCity.get(0).getCityName());
            assertNotNull(topCity.get(0).getCountry());

            System.out.println("Top 1 city for 'gbr': " + topCity.get(0).getCityName()
                    + " (" + topCity.get(0).getCountry() + ", " + topCity.get(0).getPopulation() + ")");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that Feature15.run() executes end-to-end against the live database
     * and prints the formatted report without throwing uncaught exceptions.
     */
    @Test
    void testRunFeature15EndToEnd() {
        System.out.println("\n--- TEST: RUN FEATURE 15 END-TO-END ---");
        System.out.println("Expected Result: Execute Feature15.run() against live database and display Report 15.");
        System.out.println("Actual Result  :");

        Feature15 feature15 = new Feature15();
        assertDoesNotThrow(feature15::run, "Feature15.run() should complete without throwing uncaught exceptions.");
        System.out.println("Feature15.run() completed end-to-end.");
    }
}