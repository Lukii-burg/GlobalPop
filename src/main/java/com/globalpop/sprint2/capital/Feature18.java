
package com.globalpop.sprint2.capital;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature18 {

    private static final String CONTINENT_NAME = "Asia";

    public void run() {
        System.out.println("\n--- Feature 18: All Capital Cities in a Continent ---");
        System.out.println("Selected continent: " + CONTINENT_NAME);

        try {
            List<CapitalCity> capitalCities =
                    getCapitalCitiesInContinent(CONTINENT_NAME);

            displayReport(capitalCities, CONTINENT_NAME);

        } catch (SQLException e) {
            System.err.println("Database error in Feature 18: " + e.getMessage());
        }
    }

    public List<CapitalCity> getCapitalCitiesInContinent(String continent)
            throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException("Continent cannot be empty.");
        }

        List<CapitalCity> capitalCities = new ArrayList<>();

        String query =
                "SELECT ci.Name AS CapitalCityName, " +
                        "co.Name AS CountryName, " +
                        "ci.Population AS CapitalPopulation " +
                        "FROM country co " +
                        "JOIN city ci ON co.Capital = ci.ID " +
                        "WHERE LOWER(co.Continent) = LOWER(?) " +
                        "ORDER BY ci.Population DESC, ci.Name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, continent);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    capitalCities.add(new CapitalCity(
                            rs.getString("CapitalCityName"),
                            rs.getString("CountryName"),
                            rs.getInt("CapitalPopulation")
                    ));
                }
            }
        }

        return capitalCities;
    }

    private void displayReport(
            List<CapitalCity> capitalCities, String continent) {

        if (capitalCities.isEmpty()) {
            System.out.println(
                    "No capital cities found for continent: " + continent
            );
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.printf("Report 18: CAPITAL CITIES IN CONTINENT: %s%n",
                continent.toUpperCase());
        System.out.println("Ordered by population: Largest to Smallest");
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

        System.out.println("==========================================================================");
        System.out.printf("Total capital cities displayed: %d%n",
                capitalCities.size());
    }
}
