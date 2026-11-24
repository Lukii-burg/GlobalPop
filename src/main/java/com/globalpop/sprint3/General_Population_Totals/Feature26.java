package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for generating Feature 26 Report:
 * "The population of the world."
 */
public class Feature26 {

    /**
     * Public wrapper method called from Main to execute and display the report.
     * Requires no parameters.
     */
    public void generateAndDisplayReport() {
        // Print report header banner
        System.out.println("\n==================================================================");
        System.out.println(" REPORT 26: Total Population of the World");
        System.out.println("==================================================================");

        // Fetch the total world population from the database
        long worldPopulation = getWorldPopulation();

        // Print the fetched population using package-private formatting method
        printPopulationReport(worldPopulation);
    }

    /**
     * Private method to fetch the sum of all country populations in the database.
     *
     * @return The total world population as a long (to prevent integer overflow).
     */
    private long getWorldPopulation() {
        long totalPopulation = 0;

        // SQL query to calculate the sum of the Population column across all countries
        String sqlQuery = "SELECT SUM(CAST(Population AS SIGNED)) AS TotalPopulation FROM country";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                // Extract the sum using getLong to handle numbers larger than 2.1 billion
                totalPopulation = resultSet.getLong("TotalPopulation");
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        return totalPopulation;
    }

    /**
     * Helper method to print the population in a clean format.
     * Changed from private to package-private to allow JUnit testing access.
     *
     * @param population The total population to print.
     */
    void printPopulationReport(long population) {
        // Safety check for invalid or zero population data
        if (population <= 0) {
            System.out.println("No population data available.");
            return;
        }

        // Formats the number with commas (e.g., 7,000,000,000)
        System.out.printf("World Population: %,d%n", population);
        System.out.println("------------------------------------------------------------------");
    }
}