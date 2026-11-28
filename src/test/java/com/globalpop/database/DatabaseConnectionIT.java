
package com.globalpop.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionIT {

    @Test
    void testDatabaseConnectionIsSuccessful() throws Exception {

        try (Connection connection = DatabaseConnection.getConnection()) {

            assertNotNull(
                    connection,
                    "Database connection must not be null"
            );

            assertTrue(
                    connection.isValid(5),
                    "Database connection should be valid"
            );

            assertFalse(
                    connection.isClosed(),
                    "Database connection should remain open"
            );
        }
    }

    @Test
    void testConnectedDatabaseIsWorld() throws Exception {

        try (Connection connection = DatabaseConnection.getConnection()) {

            assertEquals(
                    "world",
                    connection.getCatalog(),
                    "Application should connect to the world database"
            );
        }
    }

    @Test
    void testMySQLDatabaseProduct() throws Exception {

        try (Connection connection = DatabaseConnection.getConnection()) {

            DatabaseMetaData metadata = connection.getMetaData();

            assertNotNull(metadata);

            assertTrue(
                    metadata.getDatabaseProductName().contains("MySQL"),
                    "Database should be MySQL"
            );
        }
    }
}
