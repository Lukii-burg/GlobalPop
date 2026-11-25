
package com.globalpop.sprint2.region;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature15 {

    // Hardcoded country code and number of cities
    private static final String COUNTRY_CODE = "GBR";
    private static final int N = 10;

    /**
     * Requirement 15:
     * Display the top N most populated cities in a country.
     */
    public void run() {

        try {

            List<City> cities =
                    getTopPopulatedCities(COUNTRY_CODE, N);

            displayReport(cities, COUNTRY_CODE, N);

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    /**
     * Retrieves the top N most populated cities
     * in the selected country.
     */
    public static List<City> getTopPopulatedCities(
            String countryCode,
            int n) throws SQLException {

        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException(
                    "Country code cannot be empty."
            );
        }

        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Number of cities must be greater than zero."
            );
        }

        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co
                    ON ci.CountryCode = co.Code
                WHERE co.Code = ?
                ORDER BY ci.Population DESC, ci.Name ASC
                LIMIT ?
                """;

        List<City> cities = new ArrayList<>();

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, countryCode.toUpperCase()
            );

            statement.setInt(2, n);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    City city = new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    );

                    cities.add(city);
                }
            }
        }

        return cities;
    }

    /**
     * Displays the top populated cities report.
     */
    private static void displayReport(
            List<City> cities,
            String countryCode,
            int n) {

        System.out.println();

        System.out.println("=".repeat(100));

        System.out.println(
                "Report 15: Top " + n +
                        " populated cities in " + countryCode
        );

        System.out.println("=".repeat(100));

        System.out.printf(
                "%-30s %-30s %-25s %15s%n",
                "City Name",
                "Country",
                "District",
                "Population"
        );

        System.out.println("-".repeat(100));

        if (cities.isEmpty()) {

            System.out.println(
                    "No cities found. Please check the country code."
            );

        } else {

            for (City city : cities) {

                System.out.printf(
                        "%-30s %-30s %-25s %,15d%n",
                        city.getCityName(),
                        city.getCountry(),
                        city.getDistrict(),
                        city.getPopulation()
                );
            }
        }

        System.out.println("-".repeat(100));

        System.out.println(
                "Cities displayed: " + cities.size()
        );

        System.out.println(
                "Report completed successfully."
        );
    }
}
