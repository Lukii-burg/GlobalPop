
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

public class Feature9 {

    /**
     * Requirement 9:
     * Display all cities in a region.
     * Sort cities from largest population to smallest.
     */
    public void run() {

        // Read the region name from the user.
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();
        System.out.println("Report All City in a region");
        System.out.println("===========================");
        System.out.print("Enter region name: (e.g., Southeast Asia, Western Europe, Caribbean):");
        String region = scanner.nextLine().trim();

        // Check that the region name is not empty.
        if (region.isEmpty()) {
            System.out.println("Region name cannot be empty.");
            return;
        }

        // Find cities whose countries belong to the selected region.
        // Sort by population, with the largest cities first.
        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE co.Region = ?
                ORDER BY ci.Population DESC, ci.Name ASC
                """;

        // Store the cities returned by the database.
        List<City> cities = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Add the region name to the SQL query.
            statement.setString(1, region);

            // Run the query and read the results.
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    // Create a City object for each database record.
                    City city = new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    );

                    cities.add(city);
                }
            }

            // Display the report title and column names.
            System.out.println();
            System.out.println("=".repeat(100));
            System.out.println("Report 9: Cities in region: " + region);
            System.out.println("=".repeat(100));

            System.out.printf(
                    "%-30s %-30s %-25s %15s%n",
                    "City Name", "Country", "District", "Population"
            );

            System.out.println("-".repeat(100));

            // Display all cities in population order.
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
            System.out.println("Total cities found: " + cities.size());

            // Tell the user if no cities match the region.
            if (cities.isEmpty()) {
                System.out.println(
                        "No cities found. Please check the region name."
                );
            }

        } catch (SQLException e) {

            // Display an error if the database query fails.
            System.out.println("Database error: " + e.getMessage());
        }

        // Do not close the Scanner here because it uses System.in,
        // which may also be needed by other features.
    }
}
