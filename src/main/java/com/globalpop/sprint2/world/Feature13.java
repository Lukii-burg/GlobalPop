package com.globalpop.sprint2.world;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Feature13 {

    /**
     * Asks user for continent and N, then prints the top populated cities report.
     */
    public void run() {
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();

        System.out.println("\n--- Feature 13: Top N Populated Cities in a Continent ---");

        System.out.print("Enter Continent name (e.g., Asia, Europe, Africa): ");
        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
            return;
        }
        String continent = scanner.nextLine().trim();

        System.out.print("Enter N (Number of top populated cities to retrieve): ");
        if (!scanner.hasNextLine()) {
            System.out.println("No input available for N.");
            return;
        }

        int limit;
        try {
            limit = Integer.parseInt(scanner.nextLine().trim());
            if (limit <= 0) {
                System.out.println("Please enter a positive number greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return;
        }

        List<City> cities = getTopCitiesInContinent(continent, limit);
        displayReport(cities, continent, limit);
    }

    /**
     * Queries the database for the top N cities in a continent, sorted descending by population.
     */
    private List<City> getTopCitiesInContinent(String continent, int limit) {
        List<City> cities = new ArrayList<>();

        String query = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population " +
                "FROM city ci " +
                "JOIN country co ON ci.CountryCode = co.Code " +
                "WHERE LOWER(co.Continent) = LOWER(?) " +
                "ORDER BY ci.Population DESC " +
                "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, continent);
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

    private void displayReport(List<City> cities, String continent, int limit) {
        if (cities.isEmpty()) {
            System.out.println("\nNo cities found for continent: \"" + continent + "\"");
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf(" TOP %d POPULATED CITIES IN CONTINENT: %s%n",
                limit, continent.toUpperCase());
        System.out.println("==========================================================================================");

        System.out.printf("%-25s | %-25s | %-20s | %15s%n",
                "City", "Country", "District", "Population");

        System.out.println("------------------------------------------------------------------------------------------");

        int rank = 1;

        for (City city : cities) {
            System.out.printf("%-25s | %-25s | %-20s | %,15d%n",
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation());

            rank++;
        }

        System.out.println("==========================================================================================");
        System.out.printf("Total cities displayed: %d%n", cities.size());
    }
}