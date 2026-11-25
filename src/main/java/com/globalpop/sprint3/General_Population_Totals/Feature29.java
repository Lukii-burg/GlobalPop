
package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Feature29 {

    // Hardcoded country code
    private static final String COUNTRY_CODE = "MMR";

    /**
     * Requirement 29:
     * Display the population of a selected country.
     */
    public void run() {

        try {

            displayReport(COUNTRY_CODE);

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    /**
     * Retrieves the population information
     * of the selected country.
     */
    public static void displayReport(
            String countryCode) throws SQLException {

        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException(
                    "Country code cannot be empty."
            );
        }

        String sql = """
                SELECT
                    Code,
                    Name,
                    Continent,
                    Region,
                    Population
                FROM country
                WHERE Code = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1, countryCode.toUpperCase()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                System.out.println();

                System.out.println(
                        "============================================================"
                );

                System.out.println(
                        "Report 29: POPULATION OF A COUNTRY"
                );

                System.out.println(
                        "============================================================"
                );

                if (resultSet.next()) {

                    System.out.printf(
                            "%-20s : %s%n",
                            "Country Code",
                            resultSet.getString("Code")
                    );

                    System.out.printf(
                            "%-20s : %s%n",
                            "Country Name",
                            resultSet.getString("Name")
                    );

                    System.out.printf(
                            "%-20s : %s%n",
                            "Continent",
                            resultSet.getString("Continent")
                    );

                    System.out.printf(
                            "%-20s : %s%n",
                            "Region",
                            resultSet.getString("Region")
                    );

                    System.out.printf(
                            "%-20s : %,d%n",
                            "Total Population",
                            resultSet.getLong("Population")
                    );

                } else {

                    System.out.println(
                            "No country found with code: " + countryCode
                    );
                }

                System.out.println(
                        "============================================================"
                );

                System.out.println(
                        "Report completed successfully."
                );
            }
        }
    }
}
