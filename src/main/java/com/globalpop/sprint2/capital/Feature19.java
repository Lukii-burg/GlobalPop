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

public class Feature19 {

    /**
     * User enters region and system prints all its capital cities sorted by population.
     */
    public void run() {
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();

        System.out.println("\n--- Feature 19: All Capital Cities in a Region (Largest to Smallest) ---");
        System.out.print("Enter Region name (e.g., Southeast Asia, Western Europe, Caribbean): ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
            return;
        }

        String region = scanner.nextLine().trim();

        List<CapitalCity> capitalCities = getCapitalCitiesInRegion(region);
        displayReport(capitalCities, region);
    }

    /**
     * Queries the database for all capital cities in the specified region, sorted descending by population.
     */
    private List<CapitalCity> getCapitalCitiesInRegion(String region) {
        List<CapitalCity> capitalCities = new ArrayList<>();

        String query = "SELECT ci.Name AS CapitalCityName, co.Name AS CountryName, ci.Population AS CapitalPopulation " +
                "FROM country co " +
                "JOIN city ci ON co.Capital = ci.ID " +
                "WHERE LOWER(co.Region) = LOWER(?) " +
                "ORDER BY ci.Population DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, region);

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
            System.err.println("Database error: " + e.getMessage());
        }

        return capitalCities;
    }

    /**
     * Formats and prints the report.
     */
    private void displayReport(List<CapitalCity> capitalCities, String region) {
        if (capitalCities.isEmpty()) {
            System.out.println("\nNo capital cities found for region: \"" + region + "\"");
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.printf("Report 19: CAPITAL CITIES IN REGION: %s (LARGEST TO SMALLEST)%n", region.toUpperCase());
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