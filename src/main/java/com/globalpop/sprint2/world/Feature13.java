
package com.globalpop.sprint2.world;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature13 {

    private static final String CONTINENT_NAME = "Asia";
    private static final int TOP_LIMIT = 10;

    public void run() {
        System.out.println("\n--- Feature 13: Top N Populated Cities in a Continent ---");
        System.out.println("Continent: " + CONTINENT_NAME);
        System.out.println("Top results: " + TOP_LIMIT);

        try {
            List<City> cities =
                    getTopCitiesInContinent(CONTINENT_NAME, TOP_LIMIT);

            displayReport(cities, CONTINENT_NAME, TOP_LIMIT);
        } catch (SQLException e) {
            System.err.println("Database error in Feature 13: " + e.getMessage());
        }
    }

    private List<City> getTopCitiesInContinent(
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

    private void displayReport(
            List<City> cities, String continent, int limit) {

        if (cities.isEmpty()) {
            System.out.println("No cities found for continent: " + continent);
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf("Report 13: TOP %d POPULATED CITIES IN CONTINENT: %s%n",
                limit, continent.toUpperCase());
        System.out.println("==========================================================================================");
        System.out.printf("%-25s | %-25s | %-20s | %15s%n",
                "City", "Country", "District", "Population");
        System.out.println("------------------------------------------------------------------------------------------");

        int rank = 1;

        for (City city : cities) {
            System.out.printf("%-4d %-21s | %-25s | %-20s | %,15d%n",
                    rank++,
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation());
        }

        System.out.println("==========================================================================================");
        System.out.printf("Total cities displayed: %d%n", cities.size());
    }
}
