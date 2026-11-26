
package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.GeneralPopulationLookups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Feature 30: Reports the population of a district.
 */
public class Feature30 {

    private static final String DISTRICT_NAME = "California";
    private static final String COUNTRY_NAME = "United States";

    public void generateAndDisplayReport() {

        System.out.println("\nFeature 30: District Population Lookup");
        System.out.println("Selected district: " + DISTRICT_NAME);
        System.out.println("Country: " + COUNTRY_NAME);

        try {
            GeneralPopulationLookups report =
                    getDistrictPopulation(DISTRICT_NAME, COUNTRY_NAME);

            if (report == null) {
                System.out.println("No population data found for this district.");
                return;
            }

            displayReport(report);

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving district population: "
                            + exception.getMessage()
            );
        }
    }

    /**
     * Calculates the total recorded city population in a district.
     */
    public GeneralPopulationLookups getDistrictPopulation(
            String district, String country) throws SQLException {

        String sql = """
                SELECT
                    CONCAT(city.District, ' (', country.Name, ')') AS DistrictName,
                    SUM(city.Population) AS DistrictPopulation
                FROM city
                INNER JOIN country
                    ON city.CountryCode = country.Code
                WHERE LOWER(city.District) = LOWER(?)
                  AND LOWER(country.Name) = LOWER(?)
                GROUP BY city.District, country.Name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, district);
            statement.setString(2, country);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new GeneralPopulationLookups(
                            resultSet.getString("DistrictName"),
                            resultSet.getLong("DistrictPopulation")
                    );
                }
            }
        }

        return null;
    }

    public void displayReport(GeneralPopulationLookups report) {
        if (report == null) {
            System.out.println("\nNo population data found for this district.");
            return;
        }

        System.out.println("\n==============================================================");
        System.out.println("             DISTRICT POPULATION REPORT");
        System.out.println("==============================================================");

        System.out.printf("%-35s %s%n",
                "District",
                report.getName() != null ? report.getName() : "N/A");

        System.out.printf("%-35s %,20d%n",
                "Population",
                report.getPopulation());

        System.out.println("==============================================================");
    }
}
