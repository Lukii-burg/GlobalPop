package com.globalpop;

import com.globalpop.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature3 {

    // Inner static class to model the country record within this file
    public static class Country {
        private final String code;
        private final String name;
        private final String continent;
        private final String region;
        private final long population;
        private final String capital;

        public Country(String code, String name, String continent, String region, long population, String capital) {
            this.code = code;
            this.name = name;
            this.continent = continent;
            this.region = region;
            this.population = population;
            this.capital = capital;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public String getContinent() { return continent; }
        public String getRegion() { return region; }
        public long getPopulation() { return population; }
        public String getCapital() { return capital; }
    }

    /**
     * Executes the query for a specified region and prints the formatted report.
     *
     * @param region Target region name (e.g., "Western Europe", "Caribbean")
     */
    public static void generateReport(String region) {
        List<Country> countries = fetchCountriesInRegion(region);
        displayReport(region, countries);
    }

    private static List<Country> fetchCountriesInRegion(String region) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? " +
                "ORDER BY c.Population DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, region);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    countries.add(new Country(
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            rs.getString("Capital")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching report data: " + e.getMessage());
        }

        return countries;
    }

    private static void displayReport(String region, List<Country> countries) {
        System.out.println("==========================================================================================");
        System.out.printf(" Countries in '%s' (Largest to Smallest Population)%n", region);
        System.out.println("==========================================================================================");
        System.out.printf("%-6s | %-35s | %-15s | %-20s | %-12s | %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("------------------------------------------------------------------------------------------");

        if (countries.isEmpty()) {
            System.out.println("No records found for the specified region.");
        } else {
            for (Country country : countries) {
                System.out.printf("%-6s | %-35s | %-15s | %-20s | %,12d | %-20s%n",
                        country.getCode(),
                        country.getName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation(),
                        country.getCapital() != null ? country.getCapital() : "N/A");
            }
        }
        System.out.println("==========================================================================================\n");
    }
}