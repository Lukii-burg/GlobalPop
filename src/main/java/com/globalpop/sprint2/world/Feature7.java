package com.globalpop.sprint2.world;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for generating Feature 07 Report:
 * "All the cities in the world organised by largest population to smallest."
 */
public class Feature7 {

    /**
     * Public wrapper method called from Main to execute and display the report.
     */
    public void generateAndDisplayReport() {
        // Print report header banner
        System.out.println("\n==================================================================================================");
        System.out.println(" REPORT 7: All the Cities in the World (Organised by Population: Largest to Smallest)");
        System.out.println("==================================================================================================");

        // Fetch list of cities from database using private method
        List<City> cities = getCitiesByPopulationDescending();

        // Print fetched cities using private formatting method
        printCityReport(cities);
    }

    /**
     * Private method to fetch all cities from the database sorted by population descending.
     * Joins the 'city' table with the 'country' table to fetch the Country name.
     * Includes annotation to suppress IDE unresolved table inspection warnings.
     *
     * @return List of City objects ordered by population (highest first).
     */
    @SuppressWarnings("SqlResolve")
    private List<City> getCitiesByPopulationDescending() {
        // Initialize an empty list to store City objects
        List<City> cityList = new ArrayList<>();

        // SQL query to fetch City Name, Country Name, District, and Population
        String sqlQuery = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC";

        // Try-with-resources statement to auto-close database connection, statement, and result set
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate over each record returned by the database query
            while (resultSet.next()) {
                // Extract column values from current result row
                String cityName = resultSet.getString("Name");
                String country = resultSet.getString("CountryName");
                String district = resultSet.getString("District");
                int population = resultSet.getInt("Population");

                // Instantiate new City object with database values using the parameterized constructor
                City city = new City(cityName, country, district, population);

                // Add constructed City object to the list
                cityList.add(city);
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
            System.out.println("No city data available to display.");
            return;
        }

        // String formatting parameters for table layout alignment matching City Report columns
        String headerFormat = "%-30s %-30s %-25s %-15s%n";
        String rowFormat = "%-30s %-30s %-25s %,15d%n";

        // Print table column headers (with CITY NAME as requested)
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