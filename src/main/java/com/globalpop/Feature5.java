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

public class Feature5 {

    /**
     * Runs Feature 5.
     * Gets the continent and number of countries from the user.
     */
    public void run() {

        Scanner scanner = new Scanner(System.in);

        // Ask user for continent
        System.out.print("Enter continent: ");
        String continent = scanner.nextLine().trim();

        if (continent.isBlank()) {
            System.out.println("Continent is required.");
            return;
        }

        // Ask user for number of countries
        int limit = getValidLimit(scanner);

        try {

            List<Country> countries =
                    getTopPopulatedCountriesInContinent(
                            continent,
                            limit
                    );

            displayReport(continent, countries, limit);

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    /**
     * Gets a valid positive number from the user.
     */
    private int getValidLimit(Scanner scanner) {

        while (true) {

            System.out.print(
                    "Enter the number of top countries: "
            );

            if (scanner.hasNextInt()) {

                int limit = scanner.nextInt();
                scanner.nextLine();

                if (limit > 0) {
                    return limit;
                }

                System.out.println(
                        "Number must be greater than zero."
                );

            } else {

                System.out.println(
                        "Please enter a valid number."
                );

                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the top populated countries in the selected continent.
     */
    public List<Country> getTopPopulatedCountriesInContinent(
            String continent,
            int limit
    ) throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException(
                    "Continent is required."
            );
        }

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "Number of countries must be greater than zero."
            );
        }

        String sql = """
                SELECT
                    country.Code,
                    country.Name,
                    country.Continent,
                    country.Region,
                    country.Population,
                    capital.Name AS Capital
                FROM country
                LEFT JOIN city AS capital
                    ON country.Capital = capital.ID
                WHERE country.Continent = ?
                ORDER BY country.Population DESC
                LIMIT ?
                """;

        List<Country> countries = new ArrayList<>();

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, continent);
            statement.setInt(2, limit);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    countries.add(
                            new Country(
                                    resultSet.getString("Code"),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Continent"),
                                    resultSet.getString("Region"),
                                    resultSet.getLong("Population"),
                                    resultSet.getString("Capital")
                            )
                    );
                }
            }
        }

        return countries;
    }

    /**
     * Displays the report.
     */
    private void displayReport(
            String continent,
            List<Country> countries,
            int limit
    ) {

        System.out.println();

        System.out.println(
                "=========================================================================================="
        );

        System.out.printf(
                " TOP %d MOST POPULATED COUNTRIES IN %s%n",
                limit,
                continent.toUpperCase()
        );

        System.out.println(
                "=========================================================================================="
        );

        System.out.printf(
                "%-6s %-35s %-18s %-26s %-15s %-22s%n",
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

                if (capital == null) {
                    capital = "N/A";
                }

                System.out.printf(
                        "%-6s %-35s %-18s %-26s %,15d %-22s%n",
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
                "Countries displayed: %d%n",
                countries.size()
        );

        System.out.println(
                "Report completed successfully."
        );

        System.out.println();
    }
}
