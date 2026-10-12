package com.globalpop.sprint2.region;
import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Generates Feature 10 Report:
 * All cities in a selected country, ordered from
 * largest population to smallest.
 */
public class Feature10 {
    public void generateAndDisplayReport() {
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();

        System.out.print("Enter country name: ");

        // Check whether input is available before reading it.
        if (!scanner.hasNextLine()) {
            System.out.println("No country name provided.");
            return;
        }

        String countryName = scanner.nextLine().trim();

        if (countryName.isEmpty()) {
            System.out.println("Country name cannot be empty.");
            return;
        }

        System.out.println("\n==============================================================");
        System.out.println(" FEATURE 10: CITIES IN " + countryName.toUpperCase());
        System.out.println(" Ordered by Population: Largest to Smallest");
        System.out.println("==============================================================");

        List<City> cities = getCitiesByCountry(countryName);

        printCityReport(cities, countryName);
    }

    /**
     * Retrieves cities belonging to the selected country.
     *
     * @param countryName the country selected by the user
     * @return cities ordered by population in descending order
     */
    @SuppressWarnings("SqlResolve")
    private List<City> getCitiesByCountry(String countryName) {
        List<City> cityList = new ArrayList<>();

        String sqlQuery =
                "SELECT city.Name AS CityName, " +
                        "country.Name AS CountryName, " +
                        "city.District, city.Population " +
                        "FROM city " +
                        "JOIN country ON city.CountryCode = country.Code " +
                        "WHERE country.Name = ? " +
                        "ORDER BY city.Population DESC, city.Name ASC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sqlQuery)) {

            // Pass the country name safely into the SQL query.
            statement.setString(1, countryName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String cityName = resultSet.getString("CityName");
                    String country = resultSet.getString("CountryName");
                    String district = resultSet.getString("District");
                    int population = resultSet.getInt("Population");

                    City city = new City(
                            cityName,
                            country,
                            district,
                            population
                    );

                    cityList.add(city);
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Database error while retrieving cities: "
                            + e.getMessage()
            );
        }

        return cityList;
    }

    /**
     * Displays the city records in a formatted table.
     */
    private void printCityReport(
            List<City> cities,
            String countryName) {

        if (cities.isEmpty()) {
            System.out.println(
                    "No cities found for country: " + countryName
            );
            return;
        }

        String headerFormat = "%-30s %-30s %-25s %15s%n";
        String rowFormat = "%-30s %-30s %-25s %,15d%n";

        System.out.printf(
                headerFormat,
                "CITY NAME",
                "COUNTRY",
                "DISTRICT",
                "POPULATION"
        );

        System.out.println(
                "----------------------------------------------------------------"
                        + "--------------------------------"
        );

        for (City city : cities) {
            System.out.printf(
                    rowFormat,
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation()
            );
        }

        System.out.println(
                "----------------------------------------------------------------"
                        + "--------------------------------"
        );

        System.out.printf("Total Cities Listed: %d%n", cities.size());
    }
}
