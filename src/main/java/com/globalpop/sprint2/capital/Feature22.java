package com.globalpop.sprint2.capital;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feature 22: Displays the top N populated capital cities in a specified region.
 */
public class Feature22 {

    /**
     * Executes Feature 22: Prompts user for inputs, queries database, and prints report.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Feature 22: Top N Populated Capital Cities in a Region ---");

        System.out.print("Enter Region name (e.g., Southeast Asia, Western Europe, Caribbean): ");
        if (!scanner.hasNextLine()) {
            System.out.println("\nNo input stream available.");
            return;
        }
        String region = scanner.nextLine().trim();

        System.out.print("Enter N (Number of top populated capital cities to retrieve): ");
        if (!scanner.hasNextLine()) {
            System.out.println("\nNo input stream available for N.");
            return;
        }

        int limit;
        try {
            limit = Integer.parseInt(scanner.nextLine().trim());
            if (limit <= 0) {
                System.out.println("N must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for N. Please enter a valid number.");
            return;
        }

        List<CapitalCity> capitalCities = getTopNCapitalCitiesInRegion(region, limit);
        displayReport(capitalCities, region, limit);
    }

    /**
     * Queries the database for top N populated capital cities in a region.
     *
     * @param region Name of the region
     * @param limit  Number of records to fetch
     * @return List of CapitalCity objects
     */
    private List<CapitalCity> getTopNCapitalCitiesInRegion(String region, int limit) {
        List<CapitalCity> capitalCities = new ArrayList<>();

        // SQL Query joining country and city tables on the country's Capital ID
        String query = "SELECT ci.Name AS CapitalCityName, co.Name AS CountryName, ci.Population AS CapitalPopulation " +
                "FROM country co " +
                "JOIN city ci ON co.Capital = ci.ID " +
                "WHERE LOWER(co.Region) = LOWER(?) " +
                "ORDER BY ci.Population DESC " +
                "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, region);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCity capitalCity = new CapitalCity(
                            rs.getString("CapitalCityName"),
                            rs.getString("CountryName"),
                            rs.getInt("CapitalPopulation")
                    );
                    capitalCities.add(capitalCity);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error while fetching Feature 22 data: " + e.getMessage());
        }

        return capitalCities;
    }

    /**
     * @param capitalCities List of capital cities
     * @param region        Region name entered by user
     * @param limit         Top N number entered by user
     */
    private void displayReport(List<CapitalCity> capitalCities, String region, int limit) {
        if (capitalCities.isEmpty()) {
            System.out.println("\nNo capital cities found for region: \"" + region + "\"");
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.printf(" TOP %d POPULATED CAPITAL CITIES IN REGION: %s%n", limit, region.toUpperCase());
        System.out.println("==========================================================================");
        System.out.printf("%-30s | %-25s | %-15s%n", "Capital City", "Country", "Population");
        System.out.println("--------------------------------------------------------------------------");

        for (CapitalCity cc : capitalCities) {
            System.out.printf("%-30s | %-25s | %,15d%n",
                    cc.getCapitalCityName(),
                    cc.getCountry(),
                    cc.getPopulation());
        }
        System.out.println("==========================================================================\n");
    }
}