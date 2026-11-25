
package com.globalpop.sprint2.region;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 9:
 * Display all cities in a region, ordered by population.
 */
public class Feature9 {

    private static final String REGION_NAME = "Southeast Asia";

    public void run() {

        System.out.println("\nReport 9: All Cities in a Region");
        System.out.println("Selected region: " + REGION_NAME);

        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co ON ci.CountryCode = co.Code
                WHERE LOWER(co.Region) = LOWER(?)
                ORDER BY ci.Population DESC, ci.Name ASC
                """;

        List<City> cities = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, REGION_NAME);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    cities.add(new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    ));
                }
            }

            displayReport(cities);

        } catch (SQLException e) {
            System.err.println("Database error in Feature 9: " + e.getMessage());
        }
    }

    private void displayReport(List<City> cities) {

        System.out.println("\n" + "=".repeat(100));
        System.out.println("Report 9: Cities in Region: " + REGION_NAME);
        System.out.println("Ordered by population: Largest to Smallest");
        System.out.println("=".repeat(100));

        System.out.printf(
                "%-30s %-30s %-25s %15s%n",
                "City Name", "Country", "District", "Population"
        );

        System.out.println("-".repeat(100));

        if (cities.isEmpty()) {
            System.out.println("No cities found for this region.");
        }

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
    }
}
