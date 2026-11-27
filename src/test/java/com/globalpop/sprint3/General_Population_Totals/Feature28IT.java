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
 * Integration tests for Feature28 verifying live database connectivity,
 * aggregate SQL query execution (COUNT(*) and SUM(Population) grouped by Region),
 * missing region handling, and end-to-end report execution.
 */
class Feature28IT {

    private String targetRegion;

    /**
     * Initializes the target region parameter before each integration test runs.
     */
    @BeforeEach
    void setUp() {
        targetRegion = "Southeast Asia";
    }

    /**
     * Verifies that the live database is reachable and the aggregate SQL query
     * returns matching country count and total population for the selected region.
     */
    @Test
    void testGetRegionPopulationAggregateFromDatabase() {
        System.out.println("\n--- TEST: GET REGION POPULATION AGGREGATE FROM DB ---");
        System.out.println("Expected Result: Retrieve Region, TotalCountries, and TotalPopulation for '"
                + targetRegion + "' from live DB.");
        System.out.println("Actual Result  :");

        String sql = """
                SELECT
                    Region,
                    COUNT(*) AS TotalCountries,
                    SUM(Population) AS TotalPopulation
                FROM country
                WHERE Region = ?
                GROUP BY Region
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            assertNotNull(connection, "Database connection should not be null.");
            assertFalse(connection.isClosed(), "Database connection should be open.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            statement.setString(1, targetRegion);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next(), "Expected an aggregate row for region: " + targetRegion);

                String regionName = resultSet.getString("Region");
                int totalCountries = resultSet.getInt("TotalCountries");
                long totalPopulation = resultSet.getLong("TotalPopulation");

                assertEquals(targetRegion, regionName, "Returned region should match requested region.");
                assertTrue(totalCountries > 0, "TotalCountries should be greater than zero.");
                assertTrue(totalPopulation > 0L, "TotalPopulation should be greater than zero.");

                System.out.println("Region: " + regionName
                        + " | Countries: " + totalCountries
                        + " | Total Population: " + totalPopulation);
            }
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that displayReport(String) executes cleanly against the live database
     * when given a valid region or a non-existent region without throwing SQLException.
     */
    @Test
    void testDisplayReportValidAndMissingRegionFromDatabase() {
        System.out.println("\n--- TEST: DISPLAY REPORT FOR VALID AND MISSING REGION ---");
        System.out.println("Expected Result: Execute Feature28.displayReport() for '" + targetRegion
                + "' and for a non-existent region against live DB without errors.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            assertDoesNotThrow(
                    () -> Feature28.displayReport(targetRegion),
                    "displayReport should succeed for valid region " + targetRegion
            );
            assertDoesNotThrow(
                    () -> Feature28.displayReport("NonExistent Region XYZ"),
                    "displayReport should handle non-existent region without throwing"
            );

            System.out.println("Verified displayReport() for valid and missing regions against live DB.");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that Feature28.run() executes end-to-end against the live database
     * and prints Report 28 without throwing uncaught exceptions.
     */
    @Test
    void testRunFeature28EndToEnd() {
        System.out.println("\n--- TEST: RUN FEATURE 28 END-TO-END ---");
        System.out.println("Expected Result: Execute Feature28.run() against live database and display Report 28.");
        System.out.println("Actual Result  :");

        Feature28 feature28 = new Feature28();
        assertDoesNotThrow(feature28::run, "Feature28.run() should complete without throwing uncaught exceptions.");
        System.out.println("Feature28.run() completed end-to-end.");
    }
}