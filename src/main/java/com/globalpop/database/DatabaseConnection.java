package com.globalpop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class providing connection management for the MySQL database.
 */
public class DatabaseConnection {

    /**
     * Database host.
     *
     * Defaults to localhost for local IntelliJ execution.
     * Docker Compose sets this to "db".
     */
    private static final String HOST =
            System.getenv().getOrDefault("DB_HOST", "localhost");

    /**
     * Database port.
     *
     * Defaults to 33061 for local execution.
     * Docker Compose sets this to 3306.
     */
    private static final String PORT =
            System.getenv().getOrDefault("DB_PORT", "33061");

    /** Database name. */
    private static final String DATABASE =
            System.getenv().getOrDefault("DB_NAME", "world");

    /** Database user. */
    private static final String USER =
            System.getenv().getOrDefault("DB_USER", "root");

    /** Database password. */
    private static final String PASSWORD =
            System.getenv().getOrDefault("DB_PASSWORD", "root");

    /** JDBC connection URL. */
    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

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
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}