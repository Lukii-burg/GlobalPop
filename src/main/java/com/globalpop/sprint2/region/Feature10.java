
package com.globalpop.sprint2.region;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 10:
 * Displays all cities in a selected country,
 * ordered by population from largest to smallest.
 */
public class Feature10 {

    // Hard-coded input: change this value when required.
    private static final String COUNTRY_NAME = "Myanmar";

    public void generateAndDisplayReport() {

        try {
            List<City> cities = getCitiesByCountry(COUNTRY_NAME);
            printCityReport(cities, COUNTRY_NAME);

        } catch (SQLException e) {
            System.err.println(
                    "Database error in Feature 10: " + e.getMessage()
            );
        }
    }

    /**
     * Retrieves all cities belonging to the specified country.
     */
    public List<City> getCitiesByCountry(String countryName)
            throws SQLException {

        if (countryName == null || countryName.isBlank()) {
            throw new IllegalArgumentException(
                    "Country name cannot be empty."
            );
        }

        List<City> cityList = new ArrayList<>();

        String sql = """
                SELECT
                    ci.Name AS CityName,
                    co.Name AS CountryName,
                    ci.District,
                    ci.Population
                FROM city ci
                JOIN country co
                    ON ci.CountryCode = co.Code
                WHERE LOWER(co.Name) = LOWER(?)
                ORDER BY ci.Population DESC, ci.Name ASC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, countryName);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    City city = new City(
                            resultSet.getString("CityName"),
                            resultSet.getString("CountryName"),
                            resultSet.getString("District"),
                            resultSet.getInt("Population")
                    );

                    cityList.add(city);
                }
            }
        }

        return cityList;
    }

    /**
     * Displays the Feature 10 report.
     */
    private void printCityReport(List<City> cities, String countryName) {

        System.out.println("\n==============================================================");
        System.out.println("REPORT 10: ALL CITIES IN " + countryName.toUpperCase());
        System.out.println("ORDERED BY POPULATION: LARGEST TO SMALLEST");
        System.out.println("==============================================================");

        System.out.printf(
                "%-30s %-25s %-25s %15s%n",
                "CITY NAME",
                "COUNTRY",
                "DISTRICT",
                "POPULATION"
        );

        System.out.println(
                "-----------------------------------------------------------------------------------------------"
        );

        if (cities.isEmpty()) {
            System.out.println("No cities found for country: " + countryName);

        } else {

            for (City city : cities) {
                System.out.printf(
                        "%-30s %-25s %-25s %,15d%n",
                        city.getCityName(),
                        city.getCountry(),
                        city.getDistrict(),
                        city.getPopulation()
                );
            }
        }

        System.out.println(
                "-----------------------------------------------------------------------------------------------"
        );

        System.out.println("Total Cities Listed: " + cities.size());
        System.out.println("Feature 10 completed.\n");
    }
}
