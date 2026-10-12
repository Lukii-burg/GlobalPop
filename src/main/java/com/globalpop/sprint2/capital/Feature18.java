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
 * Feature 18:
 * Displays all capital cities in a specified continent,
 * ordered from largest population to smallest.
 */
public class Feature18 {

    /**
     * Asks the user for a continent and displays its capital city report.
     */
    public void run() {
        Scanner scanner = com.globalpop.ConsoleInput.getScanner();

        System.out.println("\n--- Feature 18: All Capital Cities in a Continent (Largest to Smallest Population) ---");
        System.out.print("Enter Continent name (e.g., Asia, Europe, Africa): ");

        if (!scanner.hasNextLine()) {
            System.out.println("No input available.");
            return;
        }

        String continent = scanner.nextLine().trim();

        if (continent.isBlank()) {
            System.out.println("Continent cannot be empty.");
            return;
        }

        List<CapitalCity> capitalCities =
                getCapitalCitiesInContinent(continent);

        displayReport(capitalCities, continent);
    }

    /**
     * Queries the database for all capital cities in a specific continent,
     * ordered from largest population to smallest.
     *
     * @param continent Name of the continent
     * @return List of capital cities in the requested continent
     */
    private List<CapitalCity> getCapitalCitiesInContinent(String continent) {
        List<CapitalCity> capitalCities = new ArrayList<>();

        String query = "SELECT ci.Name AS CapitalCityName, co.Name AS CountryName, " +
                "ci.Population AS CapitalPopulation " +
                "FROM country co " +
                "JOIN city ci ON co.Capital = ci.ID " +
                "WHERE LOWER(co.Continent) = LOWER(?) " +
                "ORDER BY ci.Population DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, continent);

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
     * Formats and prints the capital city report.
     *
     * @param capitalCities Capital cities returned from the database
     * @param continent     Continent name entered by the user
     */
    private void displayReport(
            List<CapitalCity> capitalCities,
            String continent
    ) {
        if (capitalCities.isEmpty()) {
            System.out.println("\nNo capital cities found for continent: \"" + continent + "\"");
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.printf(" CAPITAL CITIES IN CONTINENT: %s (LARGEST TO SMALLEST POPULATION)%n",
                continent.toUpperCase());
        System.out.println("==========================================================================");
        System.out.printf("%-30s | %-25s | %-15s%n",
                "Capital City", "Country", "Population");
        System.out.println("--------------------------------------------------------------------------");

        for (CapitalCity capitalCity : capitalCities) {
            System.out.printf("%-30s | %-25s | %,15d%n",
                    capitalCity.getCapitalCityName(),
                    capitalCity.getCountry(),
                    capitalCity.getPopulation());
        }

        System.out.println("==========================================================================\n");
    }
}