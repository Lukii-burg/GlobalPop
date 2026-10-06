package com.globalpop;

import com.globalpop.model.Country;
import com.globalpop.service.CountryReportService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CountryReportService reportService = new CountryReportService();

        System.out.println("GlobalPop: Top N Countries in a Continent");
        System.out.print("Enter a continent: ");
        String continent = scanner.nextLine();

        System.out.print("Enter the number of countries to display: ");
        int limit;

        try {
            limit = Integer.parseInt(scanner.nextLine());
            List<Country> countries =
                    reportService.getTopPopulatedCountriesInContinent(continent, limit);

            printCountryReport(countries);
        } catch (NumberFormatException exception) {
            System.out.println("Number of countries must be a whole number.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("Unable to retrieve the country report.");
            exception.printStackTrace();
        }
    }

    private static void printCountryReport(List<Country> countries) {
        if (countries.isEmpty()) {
            System.out.println("No countries were found for that continent.");
            return;
        }

        System.out.printf(
                "%-5s %-35s %-15s %-30s %-15s %-35s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital"
        );

        for (Country country : countries) {
            System.out.printf(
                    "%-5s %-35s %-15s %-30s %-15d %-35s%n",
                    country.code(),
                    country.name(),
                    country.continent(),
                    country.region(),
                    country.population(),
                    country.capital() == null ? "N/A" : country.capital()
            );
        }
    }
}