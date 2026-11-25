package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for generating Feature 27 Report:
 * "The population of a continent."
 */
public class Feature27 {

    /**
     * Public wrapper method called from Main to execute and display the report.
     * Uses a hardcoded parameter instead of Scanner.
     *
     * @param continent The name of the continent to calculate the population for.
     */
    public void generateAndDisplayReport(String continent) {
        // Print report header banner
        System.out.println("\n==================================================================");
        System.out.println(" REPORT 27: Total Population of Continent: " + continent);
        System.out.println("==================================================================");

        // Fetch the total population for the specified continent
        long continentPopulation = getContinentPopulation(continent);

        // Print the fetched population
        printPopulationReport(continent, continentPopulation);
    }

    /**
     * Private method to fetch the sum of populations for countries in a specific continent.
     *
     * @param continent The continent to filter by.
     * @return The total continent population as a long.
     */
    private long getContinentPopulation(String continent) {
        long totalPopulation = 0;

        // SQL query to calculate the sum of the Population column for a specific continent
        String sqlQuery = "SELECT SUM(CAST(Population AS SIGNED)) AS TotalPopulation " +
                "FROM country " +
                "WHERE Continent = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            // Set the continent parameter safely
            statement.setString(1, continent);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Extract the sum using getLong to handle large numbers safely
                    totalPopulation = resultSet.getLong("TotalPopulation");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        return totalPopulation;
    }

    /**
     * Private helper method to print the population in a clean format.
     *
     * @param continent  The continent name.
     * @param population The total population to print.
     */
    private void printPopulationReport(String continent, long population) {
        if (population <= 0) {
            System.out.println("No population data available for " + continent + ".");
            return;
        }

        // Formats the number with commas
        System.out.printf("%s Population: %,d%n", continent, population);
        System.out.println("------------------------------------------------------------------");
    }
}