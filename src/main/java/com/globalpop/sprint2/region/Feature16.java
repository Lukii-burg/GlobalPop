package com.globalpop.sprint2.region;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature16 {

    // Default hardcoded values
    private static final String DEFAULT_DISTRICT = "California";
    private static final int DEFAULT_LIMIT = 5;

    /**
     * Runs Feature 16 using the default hardcoded district and limit.
     */
    public void run() {
        run(DEFAULT_DISTRICT, DEFAULT_LIMIT);
    }

    /**
     * Queries and prints the top N populated cities in the specified district.
     */
    public void run(String district, int limit) {
        System.out.println("\n--- Feature 16: Top N Populated Cities in a District ---");

        if (district == null || district.isBlank()) {
            System.out.println("District cannot be empty.");
            return;
        }

        if (limit <= 0) {
            System.out.println("Please enter a positive number greater than 0.");
            return;
        }

        List<City> cities = getTopCitiesInDistrict(district.trim(), limit);
        displayReport(cities, district.trim(), limit);
    }

    /**
     * Queries the database for the top N cities in a specific district, sorted by population.
     */
    public List<City> getTopCitiesInDistrict(String district, int limit) {
        List<City> cities = new ArrayList<>();

        String query = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population " +
                "FROM city ci " +
                "JOIN country co ON ci.CountryCode = co.Code " +
                "WHERE LOWER(ci.District) = LOWER(?) " +
                "ORDER BY ci.Population DESC " +
                "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, district);
            stmt.setInt(2, limit);

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
     * Formats and prints the city report.
     */
    private void displayReport(List<City> cities, String district, int limit) {
        if (cities.isEmpty()) {
            System.out.println("\nNo cities found for district: \"" + district + "\"");
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf("Report 16: TOP %d POPULATED CITIES IN DISTRICT: %s%n", limit, district.toUpperCase());
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