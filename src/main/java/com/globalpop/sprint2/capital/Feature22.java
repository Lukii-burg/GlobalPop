
package com.globalpop.sprint2.capital;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 22: Displays the top N populated capital cities in a specified region.
 */
public class Feature22 {

    // Default hardcoded values
    private static final String DEFAULT_REGION = "Southeast Asia";
    private static final int DEFAULT_LIMIT = 5;

    /**
     * Executes Feature 22 using the default hardcoded region and limit.
     */
    public void run() {
        run(DEFAULT_REGION, DEFAULT_LIMIT);
    }

    /**
     * Executes Feature 22 for a given region and limit.
     */
    public void run(String region, int limit) {

        System.out.println(
                "\n--- Feature 22: Top N Populated Capital Cities in a Region ---"
        );

        if (region == null || region.isBlank()) {
            System.out.println("Region cannot be empty.");
            return;
        }

        if (limit <= 0) {
            System.out.println("N must be a positive integer.");
            return;
        }

        try {
            List<CapitalCity> capitalCities =
                    getTopNCapitalCitiesInRegion(region.trim(), limit);

            displayReport(capitalCities, region.trim(), limit);

        } catch (SQLException e) {
            System.err.println(
                    "Database error while fetching Feature 22 data: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Queries the database for the top N populated capital cities in a region.
     *
     * @param region Name of the region
     * @param limit  Number of records to fetch
     * @return List of CapitalCity objects
     * @throws SQLException if a database error occurs
     */
    public List<CapitalCity> getTopNCapitalCitiesInRegion(
            String region, int limit) throws SQLException {

        List<CapitalCity> capitalCities = new ArrayList<>();

        String query =
                "SELECT ci.Name AS CapitalCityName, "
                        + "co.Name AS CountryName, "
                        + "ci.Population AS CapitalPopulation "
                        + "FROM country co "
                        + "JOIN city ci ON co.Capital = ci.ID "
                        + "WHERE LOWER(co.Region) = LOWER(?) "
                        + "ORDER BY ci.Population DESC "
                        + "LIMIT ?";

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
        }

        return capitalCities;
    }

    /**
     * Formats and prints the capital city report.
     *
     * @param capitalCities List of capital cities
     * @param region        Region name
     * @param limit         Top N number
     */
    public void displayReport(
            List<CapitalCity> capitalCities,
            String region,
            int limit) {

        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println(
                    "\nNo capital cities found for region: \"" + region + "\""
            );
            return;
        }

        System.out.println(
                "\n=========================================================================="
        );

        System.out.printf(
                "Report 22: TOP %d POPULATED CAPITAL CITIES IN REGION: %s%n",
                limit,
                region != null ? region.toUpperCase() : "N/A"
        );

        System.out.println(
                "=========================================================================="
        );

        System.out.printf(
                "%-30s | %-25s | %-15s%n",
                "Capital City",
                "Country",
                "Population"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (CapitalCity cc : capitalCities) {

            if (cc == null) {
                continue;
            }

            System.out.printf(
                    "%-30s | %-25s | %,15d%n",
                    cc.getCapitalCityName(),
                    cc.getCountry(),
                    cc.getPopulation()
            );
        }

        System.out.println(
                "==========================================================================\n"
        );
    }
}
