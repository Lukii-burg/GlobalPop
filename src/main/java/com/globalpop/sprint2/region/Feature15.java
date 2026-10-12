
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

public class Feature15 {

    /**
     * Requirement 15:
     * Display the top N most populated cities in a country.
     * The user chooses the country and the value of N.
     */
    public void run() {

        // Read all user input as text to avoid Scanner newline problems.
        Scanner scanner = new Scanner(System.in);

        // Ask for the country code, for example GBR.
        System.out.print("Enter country code (e.g. GBR): ");
        String countryCode = scanner.nextLine().trim().toUpperCase();

        // Check that the country code is not empty.
        if (countryCode.isEmpty()) {
            System.out.println("Country code cannot be empty.");
            return;
        }

        // Keep asking until the user enters a positive whole number.
        int n;

        while (true) {
            System.out.print("Enter the number of top cities (N): ");

            // nextLine() reads the whole input line.
            String input = scanner.nextLine().trim();

            try {
                n = Integer.parseInt(input);

                if (n > 0) {
                    break;
                }

                System.out.println("N must be greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }

        // Find cities in the selected country.
        // Sort by population from largest to smallest.
        // LIMIT returns only the number of cities requested.
        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Code = ?
                ORDER BY ci.Population DESC, ci.Name ASC
                LIMIT ?
                """;

        // Store the cities returned by the query.
        List<City> cities = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Set the country code and requested number of cities.
            statement.setString(1, countryCode);
            statement.setInt(2, n);

            // Run the query and read the results.
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    // Create a City object for each result.
                    City city = new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    );

                    cities.add(city);
                }
            }

            // Display the report.
            System.out.println();
            System.out.println(
                    "Top " + n + " populated cities in " + countryCode
            );
            System.out.println("=".repeat(100));

            System.out.printf(
                    "%-30s %-30s %-25s %15s%n",
                    "City Name", "Country", "District", "Population"
            );

            System.out.println("-".repeat(100));

            // Display the cities in descending population order.
            for (City city : cities) {
                System.out.printf(
                        "%-30s %-30s %-25s %,15d%n",
                        city.getCityName(),
                        city.getCountry(),
                        city.getDistrict(),
                        city.getPopulation()
                );
            }

            System.out.println("-".repeat(100));
            System.out.println("Cities displayed: " + cities.size());

            // Show a message if the country has no matching cities.
            if (cities.isEmpty()) {
                System.out.println(
                        "No cities found. Please check the country code."
                );
            }

        } catch (SQLException e) {

            // Display an error if the database query fails.
            System.out.println("Database error: " + e.getMessage());
        }

        // Keep System.in open so other features can use it.
    }
}
