
package com.globalpop.sprint2.capital;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature21 {

    // Hardcoded continent and number of capital cities
    private static final String CONTINENT = "Asia";
    private static final int N = 10;

    /**
     * Requirement 21:
     * Display the top N most populated capital cities
     * in a selected continent.
     */
    public void run() {

        try {

            List<CapitalCity> capitalCities =
                    getTopPopulatedCapitalCities(CONTINENT, N);

            displayReport(capitalCities, CONTINENT, N);

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving capital city report."
            );

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    /**
     * Retrieves the top N most populated capital cities
     * in the selected continent.
     */
    public static List<CapitalCity> getTopPopulatedCapitalCities(
            String continent,
            int n) throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException(
                    "Continent name cannot be empty."
            );
        }

        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Number of capital cities must be greater than zero."
            );
        }

        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.Population AS Population
                FROM country co
                JOIN city ci
                    ON co.Capital = ci.ID
                WHERE co.Continent = ?
                ORDER BY ci.Population DESC, ci.Name ASC
                LIMIT ?
                """;

        List<CapitalCity> capitalCities = new ArrayList<>();

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, continent);
            statement.setInt(2, n);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    CapitalCity capitalCity = new CapitalCity(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getInt("Population")
                    );

                    capitalCities.add(capitalCity);
                }
            }
        }

        return capitalCities;
    }

    /**
     * Displays the capital city report.
     */
    private static void displayReport(
            List<CapitalCity> capitalCities,
            String continent,
            int n) {

        System.out.println();

        System.out.println(
                "=========================================================================="
        );

        System.out.println(
                "Report 21: Top " + n +
                        " populated capital cities in " + continent
        );

        System.out.println(
                "=========================================================================="
        );

        System.out.printf(
                "%-5s %-30s %-35s %15s%n",
                "Rank",
                "Capital City",
                "Country",
                "Population"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );

        if (capitalCities.isEmpty()) {

            System.out.println(
                    "No capital cities found for continent: " + continent
            );

        } else {

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
        }

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );

        System.out.println(
                "Capital cities displayed: " + capitalCities.size()
        );

        System.out.println(
                "Report completed successfully."
        );
    }
}
