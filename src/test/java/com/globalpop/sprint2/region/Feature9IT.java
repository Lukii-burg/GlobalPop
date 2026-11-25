package com.globalpop.sprint2.region;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature9 verifying live database connectivity,
 * region-based JOIN query execution between city and country tables,
 * population ordering, and end-to-end report execution.
 */
class Feature9IT {

    private String targetRegion;

    /**
     * Initializes the target region parameter before each integration test runs.
     */
    @BeforeEach
    void setUp() {
        targetRegion = "Southeast Asia";
    }

    /**
     * Verifies that the live database is reachable and returns City records
     * for the selected region ordered by population descending.
     */
    @Test
    void testGetRegionCitiesFromDatabase() {
        System.out.println("\n--- TEST: GET REGION CITIES FROM DB ---");
        System.out.println("Expected Result: Retrieve cities in '" + targetRegion
                + "' ordered by population descending from live DB.");
        System.out.println("Actual Result  :");

        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE LOWER(co.Region) = LOWER(?)
                ORDER BY ci.Population DESC, ci.Name ASC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            assertNotNull(connection, "Database connection should not be null.");
            assertFalse(connection.isClosed(), "Database connection should be open.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            statement.setString(1, targetRegion);

            List<City> cities = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cities.add(new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    ));
                }
            }

            assertNotNull(cities, "Returned city list should not be null.");
            assertFalse(cities.isEmpty(), "Returned city list should not be empty for " + targetRegion + ".");

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

            System.out.println("Retrieved " + cities.size() + " cities in " + targetRegion
                    + ". Most populated: " + cities.get(0).getCityName()
                    + " (" + cities.get(0).getCountry() + ", " + cities.get(0).getPopulation() + ")");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies query behavior against the live database when filtering by a non-existent region.
     */
    @Test
    void testNonExistentRegionReturnsEmptyResultFromDatabase() {
        System.out.println("\n--- TEST: NON-EXISTENT REGION FROM DB ---");
        System.out.println("Expected Result: Return 0 cities without errors for a region not present in the database.");
        System.out.println("Actual Result  :");

        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE LOWER(co.Region) = LOWER(?)
                ORDER BY ci.Population DESC, ci.Name ASC
                """;

        String missingRegion = "NonExistent Region XYZ";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            assertTrue(connection.isValid(2), "Database connection should be valid.");
            statement.setString(1, missingRegion);

            List<City> cities = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cities.add(new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    ));
                }
            }

            assertNotNull(cities, "Returned city list should not be null.");
            assertTrue(cities.isEmpty(), "Expected empty city list for non-existent region.");
            System.out.println("Verified 0 cities returned for region: " + missingRegion);
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that Feature9.run() executes end-to-end against the live database
     * and prints the formatted report without throwing uncaught exceptions.
     */
    @Test
    void testRunFeature9EndToEnd() {
        System.out.println("\n--- TEST: RUN FEATURE 9 END-TO-END ---");
        System.out.println("Expected Result: Execute Feature9.run() against live database and display Report 9.");
        System.out.println("Actual Result  :");

        Feature9 feature9 = new Feature9();
        assertDoesNotThrow(feature9::run, "Feature9.run() should complete without throwing uncaught exceptions.");
        System.out.println("Feature9.run() completed end-to-end.");
    }
}