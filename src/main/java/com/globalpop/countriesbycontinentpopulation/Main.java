package com.globalpop.countriesbycontinentpopulation;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CountryService countryService = new CountryService();

        System.out.println("==============================================");
        System.out.println("     COUNTRIES BY CONTINENT POPULATION");
        System.out.println("==============================================");

        System.out.println();
        System.out.println("Available continents:");
        System.out.println("Africa");
        System.out.println("Asia");
        System.out.println("Europe");
        System.out.println("North America");
        System.out.println("South America");
        System.out.println("Oceania");
        System.out.println("Antarctica");

        System.out.println();
        System.out.print("Enter continent: ");

        String continent = scanner.nextLine().trim();

        if (continent.isEmpty()) {
            System.out.println("Error: Continent cannot be empty.");
            scanner.close();
            return;
        }

        try {

            List<Country> countries =
                    countryService.getCountriesByContinent(continent);

            if (countries.isEmpty()) {
                System.out.println();
                System.out.println(
                        "No countries found for continent: " + continent
                );
                scanner.close();
                return;
            }

            System.out.println();
            System.out.println(
                    "Countries in " + continent +
                            " ordered by population:"
            );

            System.out.println(
                    "------------------------------------------------------------"
            );

            System.out.printf(
                    "%-5s %-35s %-15s%n",
                    "No.",
                    "Country",
                    "Population"
            );

            System.out.println(
                    "------------------------------------------------------------"
            );

            int position = 1;

            for (Country country : countries) {

                System.out.printf(
                        "%-5d %-35s %,d%n",
                        position,
                        country.getName(),
                        country.getPopulation()
                );

                position++;
            }

            System.out.println(
                    "------------------------------------------------------------"
            );

            System.out.println(
                    "Total countries: " + countries.size()
            );

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Database error:");
            System.out.println(e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println("Input error:");
            System.out.println(e.getMessage());

        } finally {

            scanner.close();
        }
    }
}