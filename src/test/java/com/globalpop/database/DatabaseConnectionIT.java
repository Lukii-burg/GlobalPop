package com.globalpop.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify that the application can successfully connect
 * to the live MySQL database using the configured environment variables or defaults.
 */
public class DatabaseConnectionIT {

    @Test
    void testGetConnectionSuccessfully() {
        System.out.println("\n******************************************************************");
        System.out.println("       RUNNING INTEGRATION TEST FOR DATABASE CONNECTION           ");
        System.out.println("******************************************************************");

        // Use try-with-resources to ensure the connection closes automatically after testing
        try (Connection connection = DatabaseConnection.getConnection()) {

            // 1. Verify the connection object was successfully created
            assertNotNull(connection, "Database connection should not be null.");

            // 2. Verify the connection is not closed
            assertFalse(connection.isClosed(), "Database connection should remain open after creation.");

            // 3. Ping the database to ensure the connection is truly active (2 second timeout)
            assertTrue(connection.isValid(2), "Database connection should be active and valid.");

            System.out.println("Database connection established successfully!");

        } catch (SQLException e) {
            // If any SQL error occurs (e.g., Docker is down, wrong port), fail the test explicitly
            fail("Database connection failed. Ensure Docker is running. Exception: " + e.getMessage());
        }
    }
}