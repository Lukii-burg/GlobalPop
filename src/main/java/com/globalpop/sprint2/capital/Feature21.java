
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

public class Feature21 {

    public void run() {

        // Read the continent and number of capital cities.
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();

        System.out.println(
                "\n=== Report 21: Top N Populated Capital Cities in a Continent ==="
        );

        // Get the continent name.
        System.out.print("Enter continent name(e.g Asia, Europe, Africa): ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available. Please try again.");
            return;
        }

        String continent = scanner.nextLine().trim();

        if (continent.isEmpty()) {
            System.out.println("Continent name cannot be empty.");
            return;
        }

        // Get the number of capital cities.
        System.out.print("Enter the number of capital cities (N): ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available for N. Please try again.");
            return;
        }

        String input = scanner.nextLine().trim();

        int n;

        // Check that N is a valid positive number.
        try {
            n = Integer.parseInt(input);

            if (n <= 0) {
                System.out.println(
                        "Please enter a number greater than zero."
                );
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid number. Please enter a whole number."
            );
            return;
        }

        // Find the top N populated capital cities in the continent.
        String sql =
                "SELECT ci.Name AS CityName, " +
                        "co.Name AS CountryName, " +
                        "ci.Population AS Population " +
                        "FROM country co " +
                        "JOIN city ci ON co.Capital = ci.ID " +
                        "WHERE co.Continent = ? " +
                        "ORDER BY ci.Population DESC, ci.Name ASC " +
                        "LIMIT ?";

        List<CapitalCity> capitalCities = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            // Set the continent and maximum number of results.
            statement.setString(1, continent);
            statement.setInt(2, n);

            try (ResultSet resultSet = statement.executeQuery()) {

                // Store the results using the CapitalCity model.
                while (resultSet.next()) {

                    CapitalCity capitalCity = new CapitalCity(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getInt("Population")
                    );

                    capitalCities.add(capitalCity);
                }
            }

            // Check whether any results were found.
            if (capitalCities.isEmpty()) {
                System.out.println(
                        "No capital cities found for continent: "
                                + continent
                );
                System.out.println(
                        "Please check the continent name and try again."
                );
                return;
            }

            // Display the report.
            System.out.println(
                    "\nTop " + n
                            + " populated capital cities in " + continent
            );

            System.out.printf(
                    "%-5s %-30s %-35s %15s%n",
                    "Rank", "Capital City", "Country", "Population"
            );

            System.out.println(
                    "------------------------------------------------------------------------------------------"
            );

            int rank = 1;

            for (CapitalCity capitalCity : capitalCities) {
                System.out.printf(
                        "%-5d %-30s %-35s %,15d%n",
                        rank,
                        capitalCity.getCapitalCityName(),
                        capitalCity.getCountry(),
                        capitalCity.getPopulation()
                );

                rank++;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving capital city report."
            );
            e.printStackTrace();
        }
    }
}
