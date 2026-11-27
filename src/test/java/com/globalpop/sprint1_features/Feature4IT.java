package com.globalpop.sprint1_features;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature4 verifying live database connectivity,
 * SQL query execution, descending population ordering, limit behavior,
 * and full report execution.
 */
class Feature4IT {

    private int defaultLimit;

    /**
     * Initializes default test parameters before each integration test runs.
     */
    @BeforeEach
    void setUp() {
        defaultLimit = 10;
    }

    /**
     * Verifies that the database is reachable and getTopPopulatedCountries(n)
     * returns up to n Country records ordered by population descending.
     */
    @Test
    void testGetTopPopulatedCountriesFromDatabase() {
        System.out.println("\n--- TEST: GET TOP POPULATED COUNTRIES FROM DB ---");
        System.out.println("Expected Result: Retrieve up to " + defaultLimit + " countries ordered by population descending from live DB.");
        System.out.println("Actual Result  :");

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Database connection should not be null.");
            assertFalse(connection.isClosed(), "Database connection should be open.");
            assertTrue(connection.isValid(2), "Database connection should be valid.");

            List<Country> countries = Feature4.getTopPopulatedCountries(defaultLimit);

            assertNotNull(countries, "Returned country list should not be null.");
            assertFalse(countries.isEmpty(), "Returned country list should not be empty.");
            assertTrue(countries.size() <= defaultLimit, "Returned list size should not exceed limit " + defaultLimit + ".");

            for (int i = 0; i < countries.size(); i++) {
                Country country = countries.get(i);
                assertNotNull(country, "Country entry should not be null.");
                assertNotNull(country.getCode(), "Country code should not be null.");
                assertFalse(country.getCode().isBlank(), "Country code should not be blank.");
                assertNotNull(country.getName(), "Country name should not be null.");
                assertFalse(country.getName().isBlank(), "Country name should not be blank.");
                assertTrue(country.getPopulation() >= 0, "Population should be non-negative.");

                if (i > 0) {
                    long previousPopulation = countries.get(i - 1).getPopulation();
                    assertTrue(
                            previousPopulation >= country.getPopulation(),
                            "Countries should be ordered from highest to lowest population."
                    );
                }
            }

            System.out.println("Retrieved " + countries.size() + " countries successfully. Top country: "
                    + countries.get(0).getName() + " (" + countries.get(0).getPopulation() + ")");
        } catch (SQLException e) {
            fail("Integration test failed due to database error. Ensure MySQL/Docker is running. Exception: " + e.getMessage());
        }
    }

    /**
     * Verifies that invalid non-positive limit values throw IllegalArgumentException
     * without failing unexpectedly on live database setup.
     */
    @Test
    void testInvalidLimitThrowsIllegalArgumentException() {
        System.out.println("\n--- TEST: INVALID LIMIT INPUT ---");
        System.out.println("Expected Result: Throw IllegalArgumentException for limit <= 0.");
        System.out.println("Actual Result  :");

        IllegalArgumentException zeroException = assertThrows(
                IllegalArgumentException.class,
                () -> Feature4.getTopPopulatedCountries(0)
        );
        IllegalArgumentException negativeException = assertThrows(
                IllegalArgumentException.class,
                () -> Feature4.getTopPopulatedCountries(-5)
        );

        assertEquals("Number of countries must be greater than zero.", zeroException.getMessage());
        assertEquals("Number of countries must be greater than zero.", negativeException.getMessage());

        System.out.println("Zero limit threw    : " + zeroException.getMessage());
        System.out.println("Negative limit threw: " + negativeException.getMessage());
    }

    /**
     * Verifies that Feature4.run() executes end-to-end against the live database
     * and prints the formatted report without throwing uncaught exceptions.
     */
    @Test
    void testRunFeature4EndToEnd() {
        System.out.println("\n--- TEST: RUN FEATURE 4 END-TO-END ---");
        System.out.println("Expected Result: Execute Feature4.run() against live database and display Report 4.");
        System.out.println("Actual Result  :");

        assertDoesNotThrow(Feature4::run, "Feature4.run() should complete without throwing uncaught exceptions.");
        System.out.println("Feature4.run() completed end-to-end.");
    }
}