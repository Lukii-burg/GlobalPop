package com.globalpop.countriesbycontinentpopulation;

import com.globalpop.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository {

    private static final String FIND_COUNTRIES_BY_CONTINENT = """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            WHERE Continent = ?
            ORDER BY Population DESC
            """;

    public List<Country> findCountriesByContinent(String continent)
            throws SQLException {

        List<Country> countries = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_COUNTRIES_BY_CONTINENT)) {

            statement.setString(1, continent);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Integer capital = resultSet.getObject(
                            "Capital",
                            Integer.class
                    );

                    Country country = new Country(
                            resultSet.getString("Code"),
                            resultSet.getString("Name"),
                            resultSet.getString("Continent"),
                            resultSet.getString("Region"),
                            resultSet.getLong("Population"),
                            capital
                    );

                    countries.add(country);
                }
            }
        }

        return countries;
    }
}