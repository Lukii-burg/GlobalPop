package com.globalpop.sprint1_features;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for generating Feature 01 Report:
 * "All the countries in the world organized by largest population to smallest."
 */
public class Feature1 {

    /**
     * Public wrapper method called from Main to execute and display the report.
     */
    public void generateAndDisplayReport() {
        // Print report header banner
        System.out.println("\n==========================================================================================");
        System.out.println(" REPORT: All Countries in the World (Organised by Population: Largest to Smallest)");
        System.out.println("==========================================================================================");

        // Fetch list of countries from database using private method
        List<Country> countries = getCountriesByPopulationDescending();

        // Print fetched countries using private formatting method
        printCountryReport(countries);
    }

    /**
     * Private method to fetch all countries from the database sorted by population descending.
     * Joins the 'country' table with the 'city' table to fetch the Capital city name.
     * Includes annotation to suppress IDE unresolved table inspection warnings.
     *
     * @return List of Country objects ordered by population (highest first).
     */
    @SuppressWarnings("SqlResolve")
    private List<Country> getCountriesByPopulationDescending() {
        // Initialize an empty list to store Country objects
        List<Country> countryList = new ArrayList<>();

        // SQL query to fetch Code, Name, Continent, Region, Population, and Capital city name
        // LEFT JOIN ensures countries without a mapped capital city are still included
        String sqlQuery = "SELECT country.Code, country.Name, country.Continent, country.Region, " +
                "country.Population, city.Name AS CapitalName " +
                "FROM country " +
                "LEFT JOIN city ON country.Capital = city.ID " +
                "ORDER BY country.Population DESC";

        // Try-with-resources statement to auto-close database connection, statement, and result set
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate over each record returned by the database query
            while (resultSet.next()) {
                // Extract column values from current result row
                String code = resultSet.getString("Code");
                String name = resultSet.getString("Name");
                String continent = resultSet.getString("Continent");
                String region = resultSet.getString("Region");
                long population = resultSet.getLong("Population");

                // Get capital city name; handle NULL values safely
                String capital = resultSet.getString("CapitalName");
                if (capital == null) {
                    capital = "N/A";
                }

                // Instantiate new Country object with database values
                Country country = new Country(code, name, continent, region, population, capital);

                // Add constructed Country object to the list
                countryList.add(country);
            }

        } catch (SQLException e) {
            // Print error message if a database access error occurs
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        // Return populated list of countries
        return countryList;
    }

    /**
     * Private helper method to print country records in a formatted table layout.
     *
     * @param countries List of Country records to print.
     */
    private void printCountryReport(List<Country> countries) {
        // Check if list is null or empty
        if (countries == null || countries.isEmpty()) {
            // Display notice when no data was found
            System.out.println("No country data available to display.");
            return;
        }

        // String formatting parameters for table layout alignment
        String headerFormat = "%-6s %-38s %-18s %-26s %-15s %-22s%n";
        String rowFormat = "%-6s %-38s %-18s %-26s %,15d %-22s%n";

        // Print table column headers
        System.out.printf(headerFormat, "CODE", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        // Print table divider line
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        // Iterate through each country and print formatted row
        for (Country country : countries) {
            System.out.printf(rowFormat,
                    country.getCode(),
                    country.getName(),
                    country.getContinent(),
                    country.getRegion(),
                    country.getPopulation(),
                    country.getCapital()
            );
        }

        // Print footer summary with total count
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("Total Countries Listed: %d%n", countries.size());
    }
}