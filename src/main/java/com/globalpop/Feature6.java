package com.globalpop;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feature 6: Displays the top N populated countries in a specified region.
 */
public class Feature6 {

    /**
     * Executes Feature 6: Prompts user for input, queries database, and prints report.
     */
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Feature 6: Top N Populated Countries in a Region ---");

        System.out.print("Enter Region name (e.g., Southeast Asia, Western Europe, Caribbean): ");
        String region = scanner.nextLine().trim();

        System.out.print("Enter N (Number of top populated countries to retrieve): ");
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

        List<Country> countries = getTopNCountriesInRegion(region, limit);
        displayReport(countries, region, limit);
    }

    /**
     * Queries the database for top N populated countries in a region.
     *
     * @param region Name of the region
     * @param limit  Number of records to fetch
     * @return List of Country objects
     */
    private List<Country> getTopNCountriesInRegion(String region, int limit) {
        List<Country> countries = new ArrayList<>();

        String query = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE LOWER(c.Region) = LOWER(?) " +
                "ORDER BY c.Population DESC " +
                "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, region);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Country country = new Country();
                    country.setCode(rs.getString("Code"));
                    country.setName(rs.getString("Name"));
                    country.setContinent(rs.getString("Continent"));
                    country.setRegion(rs.getString("Region"));
                    country.setPopulation(rs.getLong("Population"));
                    country.setCapital(rs.getString("Capital") != null ? rs.getString("Capital") : "N/A");

                    countries.add(country);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error while fetching Feature 6 data: " + e.getMessage());
        }

        return countries;
    }

    /**
     * Displays formatted country report.
     *
     * @param countries List of countries
     * @param region    Region name entered by user
     * @param limit     Top N number entered by user
     */
    private void displayReport(List<Country> countries, String region, int limit) {
        if (countries.isEmpty()) {
            System.out.println("\nNo countries found for region: \"" + region + "\"");
            return;
        }

        System.out.println("\n==========================================================================================================");
        System.out.printf(" TOP %d POPULATED COUNTRIES IN REGION: %s%n", limit, region.toUpperCase());
        System.out.println("==========================================================================================================");
        System.out.printf("%-6s | %-35s | %-15s | %-20s | %-15s | %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        for (Country c : countries) {
            System.out.printf("%-6s | %-35s | %-15s | %-20s | %,15d | %-20s%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapital());
        }
        System.out.println("==========================================================================================================\n");
    }
}