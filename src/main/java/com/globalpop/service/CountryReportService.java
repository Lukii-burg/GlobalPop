package com.globalpop.service;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryReportService {

    public List<Country> getTopPopulatedCountriesInContinent(
            String continent,
            int limit
    ) throws SQLException {
        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException("Continent is required.");
        }

        if (limit <= 0) {
            throw new IllegalArgumentException("Number of countries must be greater than zero.");
        }

        String sql = """
                SELECT country.Code,
                       country.Name,
                       country.Continent,
                       country.Region,
                       country.Population,
                       capital.Name AS Capital
                FROM country
                LEFT JOIN city AS capital ON country.Capital = capital.ID
                WHERE country.Continent = ?
                ORDER BY country.Population DESC
                LIMIT ?
                """;

        List<Country> countries = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, continent);
            statement.setInt(2, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    countries.add(new Country(
                            resultSet.getString("Code"),
                            resultSet.getString("Name"),
                            resultSet.getString("Continent"),
                            resultSet.getString("Region"),
                            resultSet.getInt("Population"),
                            resultSet.getString("Capital")
                    ));
                }
            }
        }

        return countries;
    }
}