package com.globalpop.sprint2.world;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature8 {

    // Default hardcoded value
    private static final String DEFAULT_CONTINENT = "Asia";

    /**
     * Runs Feature 8 using the default hardcoded continent.
     */
    public void run() {
        run(DEFAULT_CONTINENT);
    }

    /**
     * Queries and prints all cities in the specified continent by population.
     */
    public void run(String continent) {
        System.out.println("\n--- Feature 8: All Cities in a Continent (Largest to Smallest Population) ---");

        if (continent == null || continent.isBlank()) {
            System.out.println("Continent cannot be empty.");
            return;
        }

        List<City> cities = getCitiesInContinent(continent.trim());
        displayReport(cities, continent.trim());
    }

    /**
     * Queries the database for all cities in the given continent, sorted descending by population.
     */
    public List<City> getCitiesInContinent(String continent) {
        List<City> cities = new ArrayList<>();

        String query = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population " +
                "FROM city ci " +
                "JOIN country co ON ci.CountryCode = co.Code " +
                "WHERE LOWER(co.Continent) = LOWER(?) " +
                "ORDER BY ci.Population DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, continent);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    City city = new City(
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getString("District"),
                            rs.getInt("Population")
                    );
                    cities.add(city);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Formats and prints the full continent city report.
     */
    private void displayReport(List<City> cities, String continent) {
        if (cities.isEmpty()) {
            System.out.println("\nNo cities found for continent: \"" + continent + "\"");
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf("Report 8: CITIES IN CONTINENT: %s (LARGEST TO SMALLEST POPULATION)%n", continent.toUpperCase());
        System.out.println("==========================================================================================");
        System.out.printf("%-25s | %-25s | %-20s | %-15s%n", "City", "Country", "District", "Population");
        System.out.println("------------------------------------------------------------------------------------------");

        for (City c : cities) {
            System.out.printf("%-25s | %-25s | %-20s | %,15d%n",
                    c.getCityName(),
                    c.getCountry(),
                    c.getDistrict(),
                    c.getPopulation());
        }
        System.out.println("==========================================================================================\n");
    }
}