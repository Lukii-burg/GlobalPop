
package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Feature28 {

    // Hardcoded region name
    private static final String REGION = "Southeast Asia";

    /**
     * Requirement 28:
     * Display the total population of a selected region.
     */
    public void run() {

        try {

            displayReport(REGION);

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }

    /**
     * Retrieves the total population and number of countries
     * in the selected region.
     */
    public static void displayReport(
            String region) throws SQLException {

        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException(
                    "Region name cannot be empty."
            );
        }

        String sql = """
                SELECT
                    Region,
                    COUNT(*) AS TotalCountries,
                    SUM(Population) AS TotalPopulation
                FROM country
                WHERE Region = ?
                GROUP BY Region
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, region);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                System.out.println();

                System.out.println(
                        "============================================================"
                );

                System.out.println(
                        "Report 28: POPULATION OF A REGION"
                );

                System.out.println(
                        "============================================================"
                );

                if (resultSet.next()) {

                    String regionName =
                            resultSet.getString("Region");

                    int totalCountries =
                            resultSet.getInt("TotalCountries");

                    long totalPopulation =
                            resultSet.getLong("TotalPopulation");

                    System.out.printf(
                            "%-25s : %s%n",
                            "Region",
                            regionName
                    );

                    System.out.printf(
                            "%-25s : %,d%n",
                            "Number of Countries",
                            totalCountries
                    );

                    System.out.printf(
                            "%-25s : %,d%n",
                            "Total Population",
                            totalPopulation
                    );

                } else {

                    System.out.println(
                            "No population data found for region: " + region
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
