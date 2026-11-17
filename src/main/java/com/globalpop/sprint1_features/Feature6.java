package com.globalpop.sprint1_features;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feature 6:
 * Displays the top N populated countries in a specified region.
 */
public class Feature6 {
    public void execute() {

        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "\n--- Feature 6: Top N Populated Countries in a Region ---"
        );

        System.out.print(
                "Enter Region name (e.g., Southeast Asia, Western Europe, Caribbean): "
        );

        if (!scanner.hasNextLine()) {
            System.out.println(
                    "\nNo input stream available."
            );
            return;
        }

        String region = scanner.nextLine().trim();

        if (region.isBlank()) {
            System.out.println(
                    "Region cannot be empty."
            );
            return;
        }

        System.out.print(
                "Enter N (Number of top populated countries to retrieve): "
        );

        if (!scanner.hasNextLine()) {
            System.out.println(
                    "\nNo input stream available for N."
            );
            return;
        }

        String input = scanner.nextLine().trim();

        int limit;

        try {

            limit = Integer.parseInt(input);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid input for N. Please enter a valid number."
            );
            return;
        }

        if (limit <= 0) {

            System.out.println(
                    "N must be a positive integer."
            );
            return;
        }

        try {

            List<Country> countries =
                    getTopNCountriesInRegion(
                            region,
                            limit
                    );

            displayReport(
                    countries,
                    region,
                    limit
            );

        } catch (SQLException e) {

            System.err.println(
                    "Database error while fetching Feature 6 data: "
                            + e.getMessage()
            );
        }
    }

    public List<Country> getTopNCountriesInRegion(
            String region,
            int limit
    ) throws SQLException {

        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException(
                    "Region cannot be empty."
            );
        }

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "N must be a positive integer."
            );
        }

        String query = """
                SELECT
                    c.Code,
                    c.Name,
                    c.Continent,
                    c.Region,
                    c.Population,
                    ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci
                    ON c.Capital = ci.ID
                WHERE LOWER(c.Region) = LOWER(?)
                ORDER BY c.Population DESC
                LIMIT ?
                """;

        List<Country> countries = new ArrayList<>();

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(1, region);
            statement.setInt(2, limit);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Country country = new Country();

                    country.setCode(
                            resultSet.getString("Code")
                    );

                    country.setName(
                            resultSet.getString("Name")
                    );

                    country.setContinent(
                            resultSet.getString("Continent")
                    );

                    country.setRegion(
                            resultSet.getString("Region")
                    );

                    country.setPopulation(
                            resultSet.getLong("Population")
                    );

                    String capital =
                            resultSet.getString("Capital");

                    if (capital == null || capital.isBlank()) {
                        capital = "N/A";
                    }

                    country.setCapital(capital);

                    countries.add(country);
                }
            }
        }

        return countries;
    }

    /**
     * Displays the formatted country report.
     *
     * @param countries List of countries.
     * @param region Region name entered by the user.
     * @param limit Number of countries requested.
     */
    private void displayReport(
            List<Country> countries,
            String region,
            int limit) {

        System.out.println();

        System.out.println(
                "=========================================================================================================="
        );

        System.out.printf(
                " TOP %d POPULATED COUNTRIES IN REGION: %s%n",
                limit,
                region.toUpperCase()
        );

        System.out.println(
                "=========================================================================================================="
        );

        System.out.printf(
                "%-6s | %-35s | %-15s | %-20s | %-15s | %-20s%n",
                "Code",
                "Name",
                "Continent",
                "Region",
                "Population",
                "Capital"
        );

        System.out.println(
                "----------------------------------------------------------------------------------------------------------"
        );

        if (countries.isEmpty()) {

            System.out.println(
                    "No countries found for region: \""
                            + region
                            + "\""
            );

        } else {

            for (Country country : countries) {

                System.out.printf(
                        "%-6s | %-35s | %-15s | %-20s | %,15d | %-20s%n",
                        country.getCode(),
                        country.getName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation(),
                        country.getCapital()
                );
            }
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------"
        );

        System.out.printf(
                "Countries displayed: %d%n",
                countries.size()
        );

        System.out.println(
                "Report completed successfully."
        );

        System.out.println(
                "==========================================================================================================\n"
        );
    }
}