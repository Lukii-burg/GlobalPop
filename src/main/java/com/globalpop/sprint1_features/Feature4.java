
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
 * Feature 4:
 * Displays the top N most populated countries in the world.
 * N is hardcoded.
 */
public class Feature4 {

    // Hardcoded number of countries to display
    private static final int N = 10;

    public static void run() {

        try {

            List<Country> countries =
                    getTopPopulatedCountries(N);

            displayReport(countries, N);

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    /**
     * Retrieves the top N most populated countries
     * from the database.
     */
    public static List<Country> getTopPopulatedCountries(
            int n) throws SQLException {

        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Number of countries must be greater than zero."
            );
        }

        String sql = """
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
                ORDER BY c.Population DESC
                LIMIT ?
                """;

        List<Country> countries = new ArrayList<>();

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, n);

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

                    country.setCapital(
                            resultSet.getString("Capital")
                    );

                    countries.add(country);
                }
            }
        }

        return countries;
    }

    /**
     * Displays the country report.
     */
    private static void displayReport(
            List<Country> countries,
            int n) {

        System.out.println();

        System.out.println(
                "=============================================================="
        );

        System.out.println(
                " Report 4: TOP " + n +
                        " MOST POPULATED COUNTRIES IN THE WORLD"
        );

        System.out.println(
                "=============================================================="
        );

        System.out.printf(
                "%-5s %-30s %-18s %-30s %-15s %-20s%n",
                "Code",
                "Country",
                "Continent",
                "Region",
                "Population",
                "Capital"
        );

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );

        if (countries.isEmpty()) {

            System.out.println("No countries found.");

        } else {

            for (Country country : countries) {

                String capital = country.getCapital();

                if (capital == null || capital.isBlank()) {
                    capital = "N/A";
                }

                System.out.printf(
                        "%-5s %-30s %-18s %-30s %-15d %-20s%n",
                        country.getCode(),
                        country.getName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation(),
                        capital
                );
            }
        }

        System.out.println();

        System.out.println(
                "Countries displayed: " + countries.size()
        );

        System.out.println(
                "Report completed successfully."
        );
    }
}
