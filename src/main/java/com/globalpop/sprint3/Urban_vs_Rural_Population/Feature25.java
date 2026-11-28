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
 * Feature 25: Reports total population, city population,
 * and non-city population for each country.
 */
public class Feature25 {

    /**
     * Fetches the population data and prints the full country report.
     */
    public void generateAndDisplayReport() {
        System.out.println("\nFeature 25: Population Living In vs. Out of Cities by Country");

        try {
            List<PopulationLivingInVsOutOfCities> reports =
                    getPopulationReports();

            displayReport(reports);

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving country population data: "
                            + exception.getMessage()
            );
        }
    }

    /**
     * Retrieves population statistics for every country from the database.
     */
    public List<PopulationLivingInVsOutOfCities> getPopulationReports()
            throws SQLException {

        List<PopulationLivingInVsOutOfCities> reports = new ArrayList<>();

        String sql = """
                SELECT
                    country.Name AS CountryName,
                    country.Population AS TotalPopulation,
                    COALESCE(city_totals.CityPopulation, 0)
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
                ORDER BY country.Name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String country = resultSet.getString("CountryName");

                long totalPopulation =
                        resultSet.getLong("TotalPopulation");

                long urbanPopulation =
                        resultSet.getLong("UrbanPopulation");

                long ruralPopulation =
                        totalPopulation - urbanPopulation;

                reports.add(new PopulationLivingInVsOutOfCities(
                        country,
                        totalPopulation,
                        urbanPopulation,
                        ruralPopulation
                ));
            }
        }

        return reports;
    }

    /**
     * Displays population statistics for each country.
     */
    public void displayReport(
            List<PopulationLivingInVsOutOfCities> reports) {

        if (reports == null || reports.isEmpty()) {
            System.out.println("No country population data was found.");
            return;
        }

        System.out.println("\n======================================================================================================================");

        System.out.printf(
                "%-30s %18s %18s %12s %18s %12s%n",
                "Country",
                "Total Population",
                "City Population",
                "City %",
                "Non-city Population",
                "Non-city %"
        );

        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        int count = 0;
        for (PopulationLivingInVsOutOfCities report : reports) {
            if (report == null) {
                continue;
            }
            System.out.printf(
                    "%-30s %,18d %,18d %11.2f%% %,18d %11.2f%%%n",
                    report.getName() != null ? report.getName() : "N/A",
                    report.getTotalPopulation(),
                    report.getUrbanPopulation(),
                    report.getUrbanPercentage(),
                    report.getRuralPopulation(),
                    report.getRuralPercentage()
            );
            count++;
        }

        System.out.println("======================================================================================================================");
        System.out.printf("Total countries displayed: %d%n", count);
    }
}