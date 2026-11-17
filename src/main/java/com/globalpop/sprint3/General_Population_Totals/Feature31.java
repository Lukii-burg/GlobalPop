
package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.GeneralPopulationLookups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Feature 31: Reports the population of a city.
 */
public class Feature31 {

    private static final String CITY_NAME = "Los Angeles";
    private static final String COUNTRY_NAME = "United States";

    public void generateAndDisplayReport() {

        System.out.println("\nFeature 31: City Population Lookup");
        System.out.println("Selected city: " + CITY_NAME);
        System.out.println("Country: " + COUNTRY_NAME);

        try {
            GeneralPopulationLookups report =
                    getCityPopulation(CITY_NAME, COUNTRY_NAME);

            if (report == null) {
                System.out.println("No population data found for this city.");
                return;
            }

            displayReport(report);

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving city population: "
                            + exception.getMessage()
            );
        }
    }

    /**
     * Retrieves the population of a specific city.
     */
    private GeneralPopulationLookups getCityPopulation(
            String city, String country) throws SQLException {

        String sql = """
                SELECT
                    CONCAT(city.Name, ' (', country.Name, ')') AS CityName,
                    city.Population AS CityPopulation
                FROM city
                INNER JOIN country
                    ON city.CountryCode = country.Code
                WHERE LOWER(city.Name) = LOWER(?)
                  AND LOWER(country.Name) = LOWER(?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city);
            statement.setString(2, country);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new GeneralPopulationLookups(
                            resultSet.getString("CityName"),
                            resultSet.getLong("CityPopulation")
                    );
                }
            }
        }

        return null;
    }

    private void displayReport(GeneralPopulationLookups report) {

        System.out.println("\n==============================================================");
        System.out.println("                CITY POPULATION REPORT");
        System.out.println("==============================================================");

        System.out.printf("%-35s %s%n",
                "City",
                report.getName());

        System.out.printf("%-35s %,20d%n",
                "Population",
                report.getPopulation());

        System.out.println("==============================================================");
    }
}