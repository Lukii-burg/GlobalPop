package com.globalpop.sprint2.world;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 13: Displays the top N populated cities in a continent.
 */
public class Feature13 {

    private static final String CONTINENT_NAME = "Asia";
    private static final int TOP_LIMIT = 10;

    /**
     * Runs Feature 13 using the default continent and limit.
     */
    public void run() {
        run(CONTINENT_NAME, TOP_LIMIT);
    }

    /**
     * Queries and prints the top N populated cities in a given continent.
     */
    public void run(String continent, int limit) {
        System.out.println("\n--- Feature 13: Top N Populated Cities in a Continent ---");
        System.out.println("Continent: " + continent);
        System.out.println("Top results: " + limit);

        try {
            List<City> cities = getTopCitiesInContinent(continent, limit);
            displayReport(cities, continent, limit);
        } catch (SQLException e) {
            System.err.println("Database error in Feature 13: " + e.getMessage());
        }
    }

    /**
     * Queries the database for the top N cities in a continent, sorted by population descending.
     */
    public List<City> getTopCitiesInContinent(
            String continent, int limit) throws SQLException {

        List<City> cities = new ArrayList<>();

        String query =
                "SELECT ci.Name AS CityName, co.Name AS CountryName, " +
                        "ci.District, ci.Population " +
                        "FROM city ci " +
                        "JOIN country co ON ci.CountryCode = co.Code " +
                        "WHERE LOWER(co.Continent) = LOWER(?) " +
                        "ORDER BY ci.Population DESC, ci.Name ASC " +
                        "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, continent);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cities.add(new City(
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getString("District"),
                            rs.getInt("Population")
                    ));
                }
            }
        }

        return cities;
    }

    /**
     * Formats and prints the city report.
     */
    public void displayReport(
            List<City> cities, String continent, int limit) {

        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found for continent: " + continent);
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf("Report 13: TOP %d POPULATED CITIES IN CONTINENT: %s%n",
                limit, continent != null ? continent.toUpperCase() : "N/A");
        System.out.println("==========================================================================================");
        System.out.printf("%-25s | %-25s | %-20s | %15s%n",
                "City", "Country", "District", "Population");
        System.out.println("------------------------------------------------------------------------------------------");

        int rank = 1;

        for (City city : cities) {
            if (city == null) {
                continue;
            }
            System.out.printf("%-4d %-21s | %-25s | %-20s | %,15d%n",
                    rank++,
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation());
        }
        System.out.println("==========================================================================================");
        System.out.printf("Total cities displayed: %d%n", rank - 1);
    }
}