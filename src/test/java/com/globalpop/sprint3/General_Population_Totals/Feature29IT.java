package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.database.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature29 verifying live database connectivity,
 * single-country SQL query execution on the country table, case-insensitive
 * country code handling, missing code behavior, and end-to-end report execution.
 */
class Feature29IT {

    private String targetCountryCode;

    /**
     * Initializes the target country code parameter before each integration test runs.
     */
    @BeforeEach
    void setUp() {
        targetCountryCode = "MMR";
    }

    /**
     * Verifies that the live database is reachable and the SQL query returns
     * complete country population details for the selected country code.
     */
    @Test
    void testGetCountryPopulationFromDatabase() {
        System.out.println("\n--- TEST: GET COUNTRY POPULATION FROM DB ---");
        System.out.println("Expected Result: Retrieve Code, Name, Continent, Region, and Population for '"
                + targetCountryCode + "' from live DB.");
        System.out.println("Actual Result  :");

        String sql = """
                SELECT
                    Code,
                    Name,
                    Continent,
                    Region,
                    Population
                FROM country
                WHERE Code = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            assertNotNull(connection, "Database connection should not be null.");
            assertFalse(connection.isClosed(), "Database connection should be open.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            statement.setString(1, targetCountryCode.toUpperCase());

            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next(), "Expected a country record for code: " + targetCountryCode);

                String code = resultSet.getString("Code");
                String name = resultSet.getString("Name");
                String continent = resultSet.getString("Continent");
                String region = resultSet.getString("Region");
                long population = resultSet.getLong("Population");

                assertEquals(targetCountryCode, code, "Returned country code should match.");
                assertNotNull(name, "Country name should not be null.");
                assertFalse(name.isBlank(), "Country name should not be blank.");
                assertNotNull(continent, "Continent should not be null.");
                assertNotNull(region, "Region should not be null.");
                assertTrue(population >= 0L, "Population should be non-negative.");

                System.out.println("Code: " + code
                        + " | Name: " + name
                        + " | Continent: " + continent
                        + " | Region: " + region
                        + " | Population: " + population);
            }
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that displayReport(String) executes cleanly against the live database
     * for a lowercase country code and for a non-existent country code without throwing SQLException.
     */
    @Test
    void testDisplayReportLowercaseAndMissingCodeFromDatabase() {
        System.out.println("\n--- TEST: DISPLAY REPORT FOR LOWERCASE AND MISSING CODE ---");
        System.out.println("Expected Result: Execute Feature29.displayReport() for 'mmr' and for an unknown code 'ZZZ' against live DB.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            assertDoesNotThrow(
                    () -> Feature29.displayReport("mmr"),
                    "displayReport should succeed for lowercase country code 'mmr'"
            );
            assertDoesNotThrow(
                    () -> Feature29.displayReport("ZZZ"),
                    "displayReport should handle non-existent country code 'ZZZ' without throwing"
            );

            System.out.println("Verified displayReport() for lowercase and missing country codes against live DB.");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that Feature29.run() executes end-to-end against the live database
     * and prints Report 29 without throwing uncaught exceptions.
     */
    @Test
    void testRunFeature29EndToEnd() {
        System.out.println("\n--- TEST: RUN FEATURE 29 END-TO-END ---");
        System.out.println("Expected Result: Execute Feature29.run() against live database and display Report 29.");
        System.out.println("Actual Result  :");

        Feature29 feature29 = new Feature29();
        assertDoesNotThrow(feature29::run, "Feature29.run() should complete without throwing uncaught exceptions.");
        System.out.println("Feature29.run() completed end-to-end.");
    }
}