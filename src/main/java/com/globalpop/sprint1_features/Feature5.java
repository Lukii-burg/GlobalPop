
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
 * Feature 5:
 * Displays the top N most populated countries
 * in a selected continent.
 */
public class Feature5 {

    // Hard-coded inputs: change these values when required.
    private static final String CONTINENT = "Asia";
    private static final int LIMIT = 10;

    public void run() {

        try {
            List<Country> countries =
                    getTopPopulatedCountriesInContinent(CONTINENT, LIMIT);

            displayReport(CONTINENT, countries, LIMIT);

        } catch (SQLException e) {
            System.err.println("Database error in Feature 5: " + e.getMessage());
        }
    }

    /**
     * Retrieves the top N populated countries in a continent.
     */
    public List<Country> getTopPopulatedCountriesInContinent(
            String continent,
            int limit
    ) throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException("Continent cannot be empty.");
        }

        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than zero.");
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
                LEFT JOIN city capital
                    ON c.Capital = capital.ID
                WHERE LOWER(c.Continent) = LOWER(?)
                ORDER BY c.Population DESC, c.Name ASC
                LIMIT ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, continent);
            statement.setInt(2, limit);

            try (ResultSet resultSet = statement.executeQuery()) {

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
     * Displays Feature 5 report.
     */
    private void displayReport(
            String continent,
            List<Country> countries,
            int limit
    ) {

        System.out.println("\n==============================================================");
        System.out.println("REPORT 5: TOP " + limit + " POPULATED COUNTRIES IN "
                + continent.toUpperCase());
        System.out.println("==============================================================");

        System.out.printf(
                "%-6s %-30s %-15s %-25s %15s %-25s%n",
                "CODE", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL"
        );

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );

        if (countries.isEmpty()) {
            System.out.println("No countries found for continent: " + continent);
        } else {

            int rank = 1;

            for (Country country : countries) {

                String capital = country.getCapital();

                if (capital == null || capital.isBlank()) {
                    capital = "N/A";
                }

                System.out.printf(
                        "%-6s %-30s %-15s %-25s %,15d %-25s%n",
                        country.getCode(),
                        country.getName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation(),
                        capital
                );

                rank++;
            }
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );
        System.out.println("Countries displayed: " + countries.size());
        System.out.println("Feature 5 completed.\n");
    }
}
