package com.globalpop;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feature 4:
 * Displays the top N most populated countries in the world.
 *
 * N is provided by the user.
 */
public class Feature4 {

    /**
     * Runs the Top N Countries feature.
     */
    public static void run() {

        Scanner scanner = new Scanner(System.in);

        // Get N from the user
        int n = getValidN(scanner);

        // SQL query
        String sql = """
                SELECT
                    c.Code,
                    c.Name,
                    c.Continent,
                    c.Region,
                    c.Population,
                    ci.Name AS Capital
                FROM country c
                LEFT JOIN city ci
                    ON c.Capital = ci.ID
                ORDER BY c.Population DESC
                LIMIT ?
                """;

        // Store the countries returned from the database
        List<Country> countries = new ArrayList<>();

        try (
                // Use the existing database connection class
                Connection connection =
                        DatabaseConnection.getConnection();

                // Prepare SQL statement
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            // Set the user's N value
            statement.setInt(1, n);

            // Run the SQL query
            try (ResultSet resultSet =
                         statement.executeQuery()) {

                // Read each country from the database
                while (resultSet.next()) {

                    Country country = new Country();

                    country.setCode(
                            resultSet.getString("Code")
                    );

                    country.setName(
                            resultSet.getString("Name")
                    );

                    country.setContinent(
                            resultSet.getString("Continent")
                    );

                    country.setRegion(
                            resultSet.getString("Region")
                    );

                    country.setPopulation(
                            resultSet.getLong("Population")
                    );

                    country.setCapital(
                            resultSet.getString("Capital")
                    );

                    // Add Country object to the list
                    countries.add(country);
                }
            }

            // Display the report
            displayReport(countries, n);

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }


    /**
     * Gets a valid positive N from the user.
     *
     * @param scanner Scanner used to read user input.
     * @return A positive integer.
     */
    private static int getValidN(Scanner scanner) {

        while (true) {

            System.out.print(
                    "Enter the number of top countries (N): "
            );

            if (scanner.hasNextInt()) {

                int n = scanner.nextInt();

                if (n > 0) {
                    return n;
                }

                System.out.println(
                        "N must be greater than 0."
                );

            } else {

                System.out.println(
                        "Please enter a valid number."
                );

                // Remove invalid input
                scanner.next();
            }
        }
    }


    /**
     * Displays the country report.
     *
     * @param countries List of countries.
     * @param n Number requested by the user.
     */
    private static void displayReport(
            List<Country> countries,
            int n) {

        System.out.println();

        System.out.println(
                "=============================================================="
        );

        System.out.println(
                " TOP " + n +
                        " MOST POPULATED COUNTRIES IN THE WORLD"
        );

        System.out.println(
                "=============================================================="
        );


        // Table headings
        System.out.printf(
                "%-5s %-30s %-18s %-30s %-15s %-20s%n",
                "Code",
                "Country",
                "Continent",
                "Region",
                "Population",
                "Capital"
        );


        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );


        // Display each Country object
        for (Country country : countries) {

            String capital = country.getCapital();

            // Handle missing capital
            if (capital == null) {
                capital = "N/A";
            }

            System.out.printf(
                    "%-5s %-30s %-18s %-30s %-15d %-20s%n",
                    country.getCode(),
                    country.getName(),
                    country.getContinent(),
                    country.getRegion(),
                    country.getPopulation(),
                    capital
            );
        }


        System.out.println();

        System.out.println(
                "Countries displayed: " + countries.size()
        );

        System.out.println(
                "Report completed successfully."
        );
    }
}