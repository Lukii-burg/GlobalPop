
package com.globalpop.sprint2.capital;

import com.globalpop.DatabaseConnection;
import com.globalpop.model.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature17 {

    public void generateAndDisplayReport() {
        System.out.println("\n==============================================================");
        System.out.println(" Report 17: ALL CAPITAL CITIES IN THE WORLD");
        System.out.println(" Ordered by Population: Largest to Smallest");
        System.out.println("==============================================================");

        try {
            List<CapitalCity> capitalCities =
                    getCapitalCitiesByPopulationDescending();

            printCapitalCityReport(capitalCities);

        } catch (SQLException e) {
            System.err.println(
                    "Database error while retrieving capital cities: "
                            + e.getMessage()
            );
        }
    }

    private List<CapitalCity> getCapitalCitiesByPopulationDescending()
            throws SQLException {

        List<CapitalCity> capitalCityList = new ArrayList<>();

        String sqlQuery =
                "SELECT city.Name AS CapitalName, " +
                        "country.Name AS CountryName, " +
                        "city.Population " +
                        "FROM country " +
                        "JOIN city ON country.Capital = city.ID " +
                        "ORDER BY city.Population DESC, city.Name ASC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                capitalCityList.add(new CapitalCity(
                        resultSet.getString("CapitalName"),
                        resultSet.getString("CountryName"),
                        resultSet.getInt("Population")
                ));
            }
        }

        return capitalCityList;
    }

    private void printCapitalCityReport(List<CapitalCity> capitalCities) {

        if (capitalCities.isEmpty()) {
            System.out.println("No capital city data available to display.");
            return;
        }

        String headerFormat = "%-35s %-35s %15s%n";
        String rowFormat = "%-35s %-35s %,15d%n";

        System.out.printf(headerFormat,
                "CAPITAL CITY", "COUNTRY", "POPULATION");

        System.out.println("--------------------------------------------------------------------------------------");

        for (CapitalCity capitalCity : capitalCities) {
            System.out.printf(rowFormat,
                    capitalCity.getCapitalCityName(),
                    capitalCity.getCountry(),
                    capitalCity.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("Total Capital Cities Listed: %d%n",
                capitalCities.size());
    }
}
