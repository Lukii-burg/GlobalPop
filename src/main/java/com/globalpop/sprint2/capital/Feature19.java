
package com.globalpop.sprint2.capital;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature19 {

    private static final String REGION_NAME = "Southeast Asia";

    public void run() {
        System.out.println("\n--- Feature 19: All Capital Cities in a Region ---");
        System.out.println("Selected region: " + REGION_NAME);

        try {
            List<CapitalCity> capitalCities =
                    getCapitalCitiesInRegion(REGION_NAME);

            displayReport(capitalCities, REGION_NAME);

        } catch (SQLException e) {
            System.err.println("Database error in Feature 19: " + e.getMessage());
        }
    }

    private List<CapitalCity> getCapitalCitiesInRegion(String region)
            throws SQLException {

        List<CapitalCity> capitalCities = new ArrayList<>();

        String query =
                "SELECT ci.Name AS CapitalCityName, " +
                        "co.Name AS CountryName, " +
                        "ci.Population AS CapitalPopulation " +
                        "FROM country co " +
                        "JOIN city ci ON co.Capital = ci.ID " +
                        "WHERE LOWER(co.Region) = LOWER(?) " +
                        "ORDER BY ci.Population DESC, ci.Name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, region);

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
            List<CapitalCity> capitalCities, String region) {

        if (capitalCities.isEmpty()) {
            System.out.println(
                    "No capital cities found for region: " + region
            );
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.printf("Report 19: CAPITAL CITIES IN REGION: %s%n",
                region.toUpperCase());
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
