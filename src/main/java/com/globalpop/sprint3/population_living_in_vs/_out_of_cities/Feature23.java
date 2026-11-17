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
 * Feature 23: Reports total population, city population, and non-city
 * population for each continent.
 */
public class Feature23 {
    /**
     * Retrieves population statistics for every continent and displays them.
     */
    public void generateAndDisplayReport() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Feature 23: Population Living In vs. Out of Cities");
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
     * Retrieves continent population statistics from the world database.
     *
     * The city populations are grouped by country before joining country
     * records, preventing country populations from being counted repeatedly.
     *
     * @return List of population reports for the continents.
     */
    private List<PopulationLivingInVsOutOfCities> getPopulationReports() {
        List<PopulationLivingInVsOutOfCities> reports = new ArrayList<>();

        String sql = """
                SELECT
                    country.Continent AS ContinentName,
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
                GROUP BY country.Continent
                ORDER BY country.Continent
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String continent = resultSet.getString("ContinentName");
                long totalPopulation =
                        resultSet.getLong("TotalPopulation");
                long urbanPopulation =
                        resultSet.getLong("UrbanPopulation");

                // Non-city population is the remainder of the total.
                long ruralPopulation = totalPopulation - urbanPopulation;

                PopulationLivingInVsOutOfCities report =
                        new PopulationLivingInVsOutOfCities(
                                continent,
                                totalPopulation,
                                urbanPopulation,
                                ruralPopulation
                        );

                reports.add(report);
            }

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving continent population data: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays continent population statistics in a readable table.
     *
     * @param reports Population reports to display.
     */
    private void displayReport(
            List<PopulationLivingInVsOutOfCities> reports) {

        System.out.printf(
                "%-20s %18s %18s %12s %18s %12s%n",
                "Continent",
                "Total Population",
                "City Population",
                "City %",
                "Non-city Population",
                "Non-city %"
        );

        System.out.println("-".repeat(105));

        for (PopulationLivingInVsOutOfCities report : reports) {
            System.out.printf(
                    "%-20s %,18d %,18d %11.2f%% %,18d %11.2f%%%n",
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