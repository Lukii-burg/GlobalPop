package com.globalpop.sprint3.general_population_lookups;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.GeneralPopulationLookups;

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
     */
    public void generateAndDisplayReport() {
        // Print report header banner
        System.out.println("\n==========================================================================================");
        System.out.println(" REPORT: The Population of the World");
        System.out.println("==========================================================================================");

        // Fetch world population from database using private method
        GeneralPopulationLookups worldPopulation = getWorldPopulation();

        // Print fetched world population using private formatting method
        printWorldPopulationReport(worldPopulation);
    }

    /**
     * Private method to fetch the total population of the world from the database.
     *
     * @return GeneralPopulationLookup object containing "World" and its total population.
     */
    @SuppressWarnings("SqlResolve")
    private GeneralPopulationLookups getWorldPopulation() {
        GeneralPopulationLookups worldPop = null;

        // SQL query to calculate the sum of all country populations
        String sqlQuery = "SELECT SUM(Population) AS TotalPopulation FROM country";

        // Try-with-resources statement to auto-close database connection, statement, and result set
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                long population = resultSet.getLong("TotalPopulation");
                worldPop = new GeneralPopulationLookups("World", population);
            }

        } catch (SQLException e) {
            // Print error message if a database access error occurs
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        return worldPop;
    }

    /**
     * Private helper method to print the world population record in a formatted table layout.
     *
     * @param worldPop GeneralPopulationLookup record to print.
     */
    private void printWorldPopulationReport(GeneralPopulationLookups worldPop) {
        // Check if object is null
        if (worldPop == null) {
            System.out.println("No world population data available to display.");
            return;
        }

        // String formatting parameters for table layout alignment
        String headerFormat = "%-30s %-25s%n";
        String rowFormat = "%-30s %,25d%n";

        // Print table column headers
        System.out.printf(headerFormat, "AREA", "POPULATION");
        // Print table divider line
        System.out.println("------------------------------------------------------");

        // Print formatted row
        System.out.printf(rowFormat,
                worldPop.getName(),
                worldPop.getPopulation()
        );

        // Print footer summary line
        System.out.println("------------------------------------------------------");
    }
}