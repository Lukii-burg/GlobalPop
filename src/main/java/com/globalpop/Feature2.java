package com.globalpop;

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
 * Feature 2:
 * Displays all countries in a selected continent,
 * ordered from largest population to smallest.
 */
public class Feature2 {

    /**
     * Runs Feature 2 using interactive user input.
     */
    public void run() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter continent: ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
            return;
        }

        String continent = scanner.nextLine().trim();

        if (continent.isBlank()) {
            System.out.println("Continent cannot be empty.");
            return;
        }

        try {

            List<Country> countries =
                    getCountriesByContinent(continent);

            displayReport(continent, countries);

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    public List<Country> getCountriesByContinent(
            String continent) throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException(
                    "Continent cannot be empty."
            );
        }

        List<Country> countries = new ArrayList<>();

        String sql = """
                SELECT
                    c.Code,
                    c.Name,
                    c.Continent,
                    c.Region,
                    c.Population,
                    capital.Name AS Capital
                FROM country c
                LEFT JOIN city AS capital
                    ON c.Capital = capital.ID
                WHERE c.Continent = ?
                ORDER BY c.Population DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, continent);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Country country = new Country(
                            resultSet.getString("Code"),
                            resultSet.getString("Name"),
                            resultSet.getString("Continent"),
                            resultSet.getString("Region"),
                            resultSet.getLong("Population"),
                            resultSet.getString("Capital")
                    );

                    countries.add(country);
                }
            }
        }

        return countries;
    }

    /**
     * Displays the Feature 2 report.
     */
    private void displayReport(
            String continent,
            List<Country> countries) {

        System.out.println();

        System.out.println(
                "=========================================================================================="
        );

        System.out.printf(
                " COUNTRIES IN %s (LARGEST TO SMALLEST POPULATION)%n",
                continent.toUpperCase()
        );

        System.out.println(
                "=========================================================================================="
        );

        System.out.printf(
                "%-6s %-38s %-18s %-26s %-15s %-22s%n",
                "CODE",
                "NAME",
                "CONTINENT",
                "REGION",
                "POPULATION",
                "CAPITAL"
        );

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------"
        );

        if (countries.isEmpty()) {

            System.out.println(
                    "No countries found for continent: " + continent
            );

        } else {

            for (Country country : countries) {

                String capital = country.getCapital();

                if (capital == null || capital.isBlank()) {
                    capital = "N/A";
                }

                System.out.printf(
                        "%-6s %-38s %-18s %-26s %,15d %-22s%n",
                        country.getCode(),
                        country.getName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation(),
                        capital
                );
            }
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------"
        );

        System.out.printf(
                "Total Countries Listed: %d%n",
                countries.size()
        );

        System.out.println(
                "Report completed successfully."
        );

        System.out.println();
    }
}