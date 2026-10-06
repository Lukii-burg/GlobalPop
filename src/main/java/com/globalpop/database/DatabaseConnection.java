package com.globalpop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class providing connection management for the MySQL database.
 */
public class DatabaseConnection {

    /** The JDBC connection URL for the local MySQL world database instance. */
    private static final String URL = "jdbc:mysql://localhost:33061/world";

    /** Database user credential. */
    private static final String USER = "root";

    /** Database password credential. */
    private static final String PASSWORD = "root";

    /**
     * Utility private constructor to prevent instantiation.
     */
    private DatabaseConnection() {
    }

    /**
     * Establishes and returns an active database connection.
     *
     * @return Connection object to the database.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        // Return a fresh database connection instance using configured credentials
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}