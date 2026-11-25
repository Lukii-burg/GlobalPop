
package com.globalpop.sprint1_features;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 2:
 * Displays all countries in a selected continent,
 * ordered from largest population to smallest.
 */
public class Feature2 {

    // Hard-coded input: change this value when required.
    private static final String CONTINENT = "Asia";

    public void run() {
        try {
            List<Country> countries = getCountriesByContinent(CONTINENT);
            displayReport(CONTINENT, countries);

        } catch (SQLException e) {
            System.err.println("Database error in Feature 2: " + e.getMessage());
        }
    }

    /**
     * Retrieves all countries in the selected continent.
     */
    public List<Country> getCountriesByContinent(String continent)
            throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException("Continent cannot be empty.");
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
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, continent);

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
     * Displays Feature 2 report.
     */
    private void displayReport(String continent, List<Country> countries) {

        System.out.println("\n==============================================================");
        System.out.println("REPORT 2: ALL COUNTRIES IN " + continent.toUpperCase());
        System.out.println("ORDERED BY POPULATION: LARGEST TO SMALLEST");
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
            }
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );
        System.out.println("Total Countries: " + countries.size());
        System.out.println("Feature 2 completed.\n");
    }
}
