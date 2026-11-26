
package com.globalpop.sprint2.region;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature11 {

    private static final String DISTRICT_NAME = "California";

    public void run() {
        System.out.println("\n--- Feature 11: All Cities in a District ---");
        System.out.println("Selected district: " + DISTRICT_NAME);

        try {
            List<City> cities = getCitiesInDistrict(DISTRICT_NAME);
            displayReport(cities, DISTRICT_NAME);
        } catch (SQLException e) {
            System.err.println("Database error in Feature 11: " + e.getMessage());
        }
    }

    private List<City> getCitiesInDistrict(String district) throws SQLException {
        List<City> cities = new ArrayList<>();

        String query =
                "SELECT ci.Name AS CityName, co.Name AS CountryName, " +
                        "ci.District, ci.Population " +
                        "FROM city ci " +
                        "JOIN country co ON ci.CountryCode = co.Code " +
                        "WHERE LOWER(ci.District) = LOWER(?) " +
                        "ORDER BY ci.Population DESC, ci.Name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, district);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cities.add(new City(
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getString("District"),
                            rs.getInt("Population")
                    ));
                }
            }
        }

        return cities;
    }

    private void displayReport(List<City> cities, String district) {
        if (cities.isEmpty()) {
            System.out.println("No cities found for district: " + district);
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.printf("Report 11: CITIES IN DISTRICT: %s%n", district.toUpperCase());
        System.out.println("Ordered by population: Largest to Smallest");
        System.out.println("==========================================================================================");
        System.out.printf("%-25s | %-25s | %-20s | %-15s%n",
                "City", "Country", "District", "Population");
        System.out.println("------------------------------------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-25s | %-25s | %-20s | %,15d%n",
                    city.getCityName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation());
        }

        System.out.println("==========================================================================================");
        System.out.printf("Total cities displayed: %d%n", cities.size());
    }
}
