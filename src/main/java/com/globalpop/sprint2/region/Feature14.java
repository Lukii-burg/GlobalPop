package com.globalpop.sprint2.region;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for generating Feature 14 Report:
 * "The top N populated cities in a region where N is provided by the user."
 */
public class Feature14 {

    /**
     * Public wrapper method called from Main to prompt the user for input,
     * execute the database query, and display the report.
     */
    public void generateAndDisplayReport() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user to provide the region name safely using hasNextLine()
        System.out.print("Enter the region name (e.g., Western Europe, Caribbean): ");
        String region = "";
        if (scanner.hasNextLine()) {
            region = scanner.nextLine().trim();
        }

        // Prompt user to provide N (number of top cities) safely using hasNextLine()
        System.out.print("Enter value for N (number of top populated cities to show): ");
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
        System.out.println(" REPORT 14: Top " + n + " Populated Cities in Region: " + region);
        System.out.println("==================================================================================================");

        // Fetch list of top N cities from database using private method
        List<City> cities = getTopNCitiesInRegion(region, n);

        // Print fetched cities using private formatting method
        printCityReport(cities);
    }

    /**
     * Private method to fetch the top N cities in a specific region sorted by population descending.
     * Joins the 'city' table with the 'country' table to filter by Region, and applies a LIMIT clause.
     * Includes annotation to suppress IDE unresolved table inspection warnings.
     *
     * @param region The geographic region name.
     * @param n      The maximum number of records to return.
     * @return List of City objects ordered by population (highest first).
     */
    @SuppressWarnings("SqlResolve")
    private List<City> getTopNCitiesInRegion(String region, int n) {
        // Initialize an empty list to store City objects
        List<City> cityList = new ArrayList<>();

        // SQL query to fetch City Name, Country Name, District, and Population filtered by Region with a limit
        String sqlQuery = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        // Try-with-resources statement to auto-close database connection and statement
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            // Set parameters for the prepared statement safely
            statement.setString(1, region);
            statement.setInt(2, n);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Iterate over each record returned by the database query
                while (resultSet.next()) {
                    // Extract column values from current result row
                    String cityName = resultSet.getString("Name");
                    String country = resultSet.getString("CountryName");
                    String district = resultSet.getString("District");
                    int population = resultSet.getInt("Population");

                    // Instantiate new City object with database values
                    City city = new City(cityName, country, district, population);

                    // Add constructed City object to the list
                    cityList.add(city);
                }
            }

        } catch (SQLException e) {
            // Print error message if a database access error occurs
            System.err.println("Database error occurred while generating report: " + e.getMessage());
        }

        // Return populated list of cities
        return cityList;
    }

    /**
     * Private helper method to print city records in a formatted table layout.
     *
     * @param cities List of City records to print.
     */
    private void printCityReport(List<City> cities) {
        // Check if list is null or empty
        if (cities == null || cities.isEmpty()) {
            // Display notice when no data was found
            System.out.println("No city data available to display for this region.");
            return;
        }

        // String formatting parameters for table layout alignment matching City Report columns
        String headerFormat = "%-30s %-30s %-25s %-15s%n";
        String rowFormat = "%-30s %-30s %-25s %,15d%n";

        // Print table column headers
        System.out.printf(headerFormat, "CITY NAME", "COUNTRY", "DISTRICT", "POPULATION");
        // Print table divider line
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        // Iterate through each city and print formatted row using getCityName()
        for (City city : cities) {
            System.out.printf(rowFormat,
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation()
            );
        }

        // Print footer summary with total count
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("Total Cities Listed: %d%n", cities.size());
    }
}