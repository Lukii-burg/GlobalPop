package com.globalpop.sprint3.Urban_vs_Rural_Population;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.PopulationLivingInVsOutOfCities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 24: Reports total population, city population,
 * and non-city population for each region.
 */
public class Feature24 {

    public void generateAndDisplayReport() {
        System.out.println("\nFeature 24: Population Living In vs. Out of Cities by Region");

        try {
            List<PopulationLivingInVsOutOfCities> reports =
                    getPopulationReports();

            if (reports.isEmpty()) {
                System.out.println("No population data was found.");
                return;
            }

            displayReport(reports);

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving region population data: "
                            + exception.getMessage()
            );
        }
    }

    /**
     * Retrieves population statistics for every region.
     */
    private List<PopulationLivingInVsOutOfCities> getPopulationReports()
            throws SQLException {

        List<PopulationLivingInVsOutOfCities> reports = new ArrayList<>();

        String sql = """
                SELECT
                    country.Region AS RegionName,
                    SUM(country.Population) AS TotalPopulation,
                    SUM(COALESCE(city_totals.CityPopulation, 0))
                        AS UrbanPopulation
                FROM country
                LEFT JOIN (
                    SELECT
                        CountryCode,
                        SUM(Population) AS CityPopulation
                    FROM city
                    GROUP BY CountryCode
                ) AS city_totals
                    ON country.Code = city_totals.CountryCode
                GROUP BY country.Region
                ORDER BY country.Region
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String region =
                        resultSet.getString("RegionName");

                long totalPopulation =
                        resultSet.getLong("TotalPopulation");

                long urbanPopulation =
                        resultSet.getLong("UrbanPopulation");

                long ruralPopulation =
                        totalPopulation - urbanPopulation;

                reports.add(new PopulationLivingInVsOutOfCities(
                        region,
                        totalPopulation,
                        urbanPopulation,
                        ruralPopulation
                ));
            }
        }

        return reports;
    }

    /**
     * Displays region population statistics.
     */
    private void displayReport(
            List<PopulationLivingInVsOutOfCities> reports) {

        System.out.println("\n======================================================================================================================");

        System.out.printf(
                "%-30s %18s %18s %12s %18s %12s%n",
                "Region",
                "Total Population",
                "City Population",
                "City %",
                "Non-city Population",
                "Non-city %"
        );

        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        for (PopulationLivingInVsOutOfCities report : reports) {
            System.out.printf(
                    "%-30s %,18d %,18d %11.2f%% %,18d %11.2f%%%n",
                    report.getName(),
                    report.getTotalPopulation(),
                    report.getUrbanPopulation(),
                    report.getUrbanPercentage(),
                    report.getRuralPopulation(),
                    report.getRuralPercentage()
            );
        }

        System.out.println("======================================================================================================================");
        System.out.printf("Total regions displayed: %d%n", reports.size());
    }
}

