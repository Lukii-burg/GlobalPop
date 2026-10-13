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

public class Feature12 {

    /**
     * Asks the user for N and prints the world city report.
     */
    public void run() {
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();

        System.out.println("\n--- Feature 12: Top N Populated Cities in the World ---");
        System.out.print("Enter N (Number of top populated cities): ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
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

        List<City> cities = getTopWorldCities(limit);
        displayReport(cities, limit);
    }

    /**
     * Queries the database for the top N cities globally, sorted by population.
     */
    private List<City> getTopWorldCities(int limit) {
        List<City> cities = new ArrayList<>();

        String query = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population " +
                "FROM city ci " +
                "JOIN country co ON ci.CountryCode = co.Code " +
                "ORDER BY ci.Population DESC " +
                "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, limit);

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
     * Formats and prints the city report
     */
    private void displayReport(List<City> cities, int limit) {
        if (cities.isEmpty()) {
            System.out.println("\nNo cities found.");
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf("Report 12: TOP %d POPULATED CITIES IN THE WORLD%n", limit);
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