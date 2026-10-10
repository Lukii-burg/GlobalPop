package com.globalpop.sprint2.capital;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for generating Feature 20 Report:
 * "The top N populated capital cities in the world where N is provided by the user."
 */
public class Feature20 {

    /**
     * Public wrapper method called from Main to prompt the user for input,
     * execute the database query, and display the report.
     */
    public void generateAndDisplayReport() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to provide N (number of top capital cities) safely using hasNextLine()
        System.out.print("Enter value for N (number of top populated capital cities to show): ");
        int n = 5; // Default fallback value
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            try {
                n = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number provided. Defaulting N to 5.");
            }
        }

        // Print report header banner
        System.out.println("\n==================================================================================================");
        System.out.println(" REPORT 20: Top " + n + " Populated Capital Cities in the World");
        System.out.println("==================================================================================================");

        // Fetch list of top N capital cities from database using private method
        List<CapitalCity> capitalCities = getTopNCapitalCitiesInWorld(n);

        // Print fetched capital cities using private formatting method
        printCapitalCityReport(capitalCities);
    }

    /**
     * Private method to fetch the top N capital cities in the world sorted by population descending.
     * Joins the 'country' table with the 'city' table on country.Capital = city.ID, and applies a LIMIT clause.
     * Includes annotation to suppress IDE unresolved table inspection warnings.
     *
     * @param n The maximum number of records to return.
     * @return List of CapitalCity objects ordered by population (highest first).
     */
    @SuppressWarnings("SqlResolve")
    private List<CapitalCity> getTopNCapitalCitiesInWorld(int n) {
        // Initialize an empty list to store CapitalCity objects
        List<CapitalCity> capitalCityList = new ArrayList<>();

        // SQL query to join country and city tables matching the capital ID, sorted by population descending with limit
        String sqlQuery = "SELECT city.Name AS CapitalName, country.Name AS CountryName, city.Population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        // Try-with-resources statement to auto-close database connection and statement
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            // Set parameter for the prepared statement safely
            statement.setInt(1, n);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Iterate over each record returned by the database query
                while (resultSet.next()) {
                    // Extract column values from current result row
                    String capitalCityName = resultSet.getString("CapitalName");
                    String country = resultSet.getString("CountryName");
                    int population = resultSet.getInt("Population");

                    // Instantiate new CapitalCity object with database values
                    CapitalCity capitalCity = new CapitalCity(capitalCityName, country, population);

                    // Add constructed CapitalCity object to the list
                    capitalCityList.add(capitalCity);
                }
            }

        } catch (SQLException e) {
            // Print error message if a database access error occurs
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        // Return populated list of capital cities
        return capitalCityList;
    }

    /**
     * Private helper method to print capital city records in a formatted table layout.
     *
     * @param capitalCities List of CapitalCity records to print.
     */
    private void printCapitalCityReport(List<CapitalCity> capitalCities) {
        // Check if list is null or empty
        if (capitalCities == null || capitalCities.isEmpty()) {
            // Display notice when no data was found
            System.out.println("No capital city data available to display.");
            return;
        }

        // String formatting parameters for table layout alignment matching Capital City Report columns
        String headerFormat = "%-30s %-30s %-15s%n";
        String rowFormat = "%-30s %-30s %,15d%n";

        // Print table column headers
        System.out.printf(headerFormat, "CAPITAL CITY", "COUNTRY", "POPULATION");
        // Print table divider line
        System.out.println("--------------------------------------------------------------------------------------------------");

        // Iterate through each capital city and print formatted row using getCapitalCityName()
        for (CapitalCity capitalCity : capitalCities) {
            System.out.printf(rowFormat,
                    capitalCity.getCapitalCityName(),
                    capitalCity.getCountry(),
                    capitalCity.getPopulation()
            );
        }

        // Print footer summary with total count
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("Total Capital Cities Listed: %d%n", capitalCities.size());
    }
}