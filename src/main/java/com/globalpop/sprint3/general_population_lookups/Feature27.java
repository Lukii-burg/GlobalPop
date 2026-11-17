package com.globalpop.sprint3.general_population_lookups;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.GeneralPopulationLookups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for generating Feature 27 Report:
 * "The population of a continent."
 */
public class Feature27 {

    /**
     * Public wrapper method called from Main to execute and display the report.
     */
    public void generateAndDisplayReport() {
        // Print report header banner
        System.out.println("\n==========================================================================================");
        System.out.println(" REPORT: The Population of Each Continent");
        System.out.println("==========================================================================================");

        // Fetch continent populations from database using private method
        List<GeneralPopulationLookups> continentPopulations = getContinentPopulations();

        // Print fetched continent populations using private formatting method
        printContinentPopulationReport(continentPopulations);
    }

    /**
     * Private method to fetch the total population for each continent from the database.
     *
     * @return List of GeneralPopulationLookup objects containing each continent and its total population.
     */
    @SuppressWarnings("SqlResolve")
    private List<GeneralPopulationLookups> getContinentPopulations() {
        List<GeneralPopulationLookups> continentList = new ArrayList<>();

        // SQL query to group by continent and sum populations, ordered by population descending
        String sqlQuery = "SELECT Continent, SUM(Population) AS TotalPopulation " +
                "FROM country " +
                "GROUP BY Continent " +
                "ORDER BY TotalPopulation DESC";

        // Try-with-resources statement to auto-close database connection, statement, and result set
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String continentName = resultSet.getString("Continent");
                long population = resultSet.getLong("TotalPopulation");

                GeneralPopulationLookups lookup = new GeneralPopulationLookups(continentName, population);
                continentList.add(lookup);
            }

        } catch (SQLException e) {
            // Print error message if a database access error occurs
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        return continentList;
    }

    /**
     * Private helper method to print continent population records in a formatted table layout.
     *
     * @param continentPopulations List of GeneralPopulationLookup records to print.
     */
    private void printContinentPopulationReport(List<GeneralPopulationLookups> continentPopulations) {
        // Check if list is null or empty
        if (continentPopulations == null || continentPopulations.isEmpty()) {
            System.out.println("No continent population data available to display.");
            return;
        }

        // String formatting parameters for table layout alignment
        String headerFormat = "%-30s %-25s%n";
        String rowFormat = "%-30s %,25d%n";

        // Print table column headers
        System.out.printf(headerFormat, "CONTINENT", "POPULATION");
        // Print table divider line
        System.out.println("------------------------------------------------------");

        // Iterate through each continent and print formatted row
        for (GeneralPopulationLookups lookup : continentPopulations) {
            System.out.printf(rowFormat,
                    lookup.getName(),
                    lookup.getPopulation()
            );
        }

        // Print footer summary line
        System.out.println("------------------------------------------------------");
        System.out.printf("Total Continents Listed: %d%n", continentPopulations.size());
    }
}