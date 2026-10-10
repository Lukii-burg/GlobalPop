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

public class Feature16 {

    /**
     * Asks user for district and N, then prints the city report.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Feature 16: Top N Populated Cities in a District ---");

        System.out.print("Enter District name (e.g., California, Noord-Holland, Île-de-France): ");
        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
            return;
        }
        String district = scanner.nextLine().trim();

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

        List<City> cities = getTopCitiesInDistrict(district, limit);
        displayReport(cities, district, limit);
    }

    /**
     * Queries the database for the top N cities in a specific district, sorted by population.
     */
    private List<City> getTopCitiesInDistrict(String district, int limit) {
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
        System.out.printf(" TOP %d POPULATED CITIES IN DISTRICT: %s%n", limit, district.toUpperCase());
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