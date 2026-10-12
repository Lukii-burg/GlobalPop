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
 * Feature 11:
 * Displays all cities in a specified district,
 * ordered from largest population to smallest.
 */
public class Feature11 {

    /**
     * Asks the user for a district and displays its city report.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Feature 11: All Cities in a District (Largest to Smallest Population) ---");
        System.out.print("Enter District name (e.g., California, Noord-Holland, Île-de-France): ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
            return;
        }

        String district = scanner.nextLine().trim();

        if (district.isBlank()) {
            System.out.println("District cannot be empty.");
            return;
        }

        List<City> cities = getCitiesInDistrict(district);
        displayReport(cities, district);
    }

    /**
     * Queries the database for all cities in a specific district,
     * ordered from largest population to smallest.
     *
     * @param district Name of the district
     * @return List of cities in the requested district
     */
    private List<City> getCitiesInDistrict(String district) {
        List<City> cities = new ArrayList<>();

        String query = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, ci.Population " +
                "FROM city ci " +
                "JOIN country co ON ci.CountryCode = co.Code " +
                "WHERE LOWER(ci.District) = LOWER(?) " +
                "ORDER BY ci.Population DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, district);

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
     * Formats and prints the district city report.
     *
     * @param cities   Cities returned from the database
     * @param district District name entered by the user
     */
    private void displayReport(List<City> cities, String district) {
        if (cities.isEmpty()) {
            System.out.println("\nNo cities found for district: \"" + district + "\"");
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf(" CITIES IN DISTRICT: %s (LARGEST TO SMALLEST POPULATION)%n", district.toUpperCase());
        System.out.println("==========================================================================================");
        System.out.printf("%-25s | %-25s | %-20s | %-15s%n", "City", "Country", "District", "Population");
        System.out.println("------------------------------------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-25s | %-25s | %-20s | %,15d%n",
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation());
        }

        System.out.println("==========================================================================================\n");
    }
}