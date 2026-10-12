package com.globalpop.sprint3.population_living_in_vs._out_of_cities;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.PopulationLivingInVsOutOfCities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feature 24: Reports total population, city population, and non-city
 * population for each region.
 */
public class Feature24 {

    /**
     * Retrieves population statistics for every region and displays them.
     */
    public void generateAndDisplayReport() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Feature 24: Population Living In vs. Out of Cities");
        System.out.println("Press Enter to generate the report.");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        List<PopulationLivingInVsOutOfCities> populationReports =
                getPopulationReports();

        if (populationReports.isEmpty()) {
            System.out.println("No population data was found.");
            return;
        }

        displayReport(populationReports);
    }

    /**
     * Retrieves region population statistics from the world database.
     *
     * The city populations are grouped by country before joining country
     * records, preventing country populations from being counted repeatedly.
     *
     * @return List of population reports for the regions.
     */
    private List<PopulationLivingInVsOutOfCities> getPopulationReports() {
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
                String region = resultSet.getString("RegionName");
                long totalPopulation =
                        resultSet.getLong("TotalPopulation");
                long urbanPopulation =
                        resultSet.getLong("UrbanPopulation");

                // Non-city population is the remainder of the total.
                long ruralPopulation = totalPopulation - urbanPopulation;

                PopulationLivingInVsOutOfCities report =
                        new PopulationLivingInVsOutOfCities(
                                region,
                                totalPopulation,
                                urbanPopulation,
                                ruralPopulation
                        );

                reports.add(report);
            }

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving region population data: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays region population statistics in a readable table.
     *
     * @param reports Population reports to display.
     */
    private void displayReport(
            List<PopulationLivingInVsOutOfCities> reports) {

        System.out.printf(
                "%-30s %18s %18s %12s %18s %12s%n",
                "Region",
                "Total Population",
                "City Population",
                "City %",
                "Non-city Population",
                "Non-city %"
        );

        System.out.println("-".repeat(115));

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
    }
}