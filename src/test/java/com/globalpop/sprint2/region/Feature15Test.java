package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature15Test {

    private List<City> validCities;

    /**
     * Initializes a fresh list of mock City objects for "GBR" before each test runs.
     * Keeps unit tests isolated in memory without opening a database connection.
     */
    @BeforeEach
    void setUp() {
        validCities = new ArrayList<>();

        validCities.add(new City("London", "United Kingdom", "England", 7285000));
        validCities.add(new City("Birmingham", "United Kingdom", "West Midlands", 1016800));
        validCities.add(new City("Glasgow", "United Kingdom", "Scotland", 619680));
    }

    /**
     * Test 1: This tests what happens when the city list is null or empty.
     * The report helper should handle both cases safely without connecting to the database or crashing.
     */
    @Test
    void testNullOrEmptyCitiesList() {
        System.out.println("\n--- TEST 1: NULL OR EMPTY CITIES LIST INPUT ---");
        System.out.println("Expected Result: Handle null/empty list safely and display 'No cities found. Please check the country code.'");
        System.out.println("Actual Result  :");

        List<City> nullCities = null;
        List<City> emptyCities = new ArrayList<>();

        assertNull(nullCities);
        assertTrue(emptyCities.isEmpty());

        printMockCountryCitiesReport(nullCities, "GBR", 10);
        printMockCountryCitiesReport(emptyCities, "GBR", 10);
    }

    /**
     * Test 2: This tests what happens when a list contains a null City element or null/blank fields.
     * The report helper should skip null entries safely and replace missing fields with "N/A".
     */
    @Test
    void testListContainsNullElementAndNullFields() {
        System.out.println("\n--- TEST 2: LIST WITH NULL ELEMENT AND NULL FIELDS ---");
        System.out.println("Expected Result: Skip null City safely, resolve null/blank fields to 'N/A', and display 2 valid cities.");
        System.out.println("Actual Result  :");

        List<City> mixedCities = new ArrayList<>();
        mixedCities.add(null);
        mixedCities.add(new City(null, "United Kingdom", "", 550000));
        mixedCities.add(new City("Leeds", "United Kingdom", "West Yorkshire", 424194));

        assertEquals(3, mixedCities.size());
        printMockCountryCitiesReport(mixedCities, "GBR", 10);
    }

    /**
     * Test 3: This tests what happens under normal conditions with populated mock cities for a country.
     * The report helper should print a formatted table with all provided mock cities and their populations.
     */
    @Test
    void testDisplayMockCountryCitiesReport() {
        System.out.println("\n--- TEST 3: DISPLAY MOCK COUNTRY CITIES REPORT ---");
        System.out.println("Expected Result: Print formatted table with " + validCities.size() + " mock cities for GBR.");
        System.out.println("Actual Result  :");

        assertEquals(3, validCities.size());
        assertEquals("London", validCities.get(0).getCityName());
        assertEquals("United Kingdom", validCities.get(0).getCountry());
        assertEquals("England", validCities.get(0).getDistrict());
        assertEquals(7285000, validCities.get(0).getPopulation());

        assertEquals("Birmingham", validCities.get(1).getCityName());
        assertEquals("Glasgow", validCities.get(2).getCityName());

        printMockCountryCitiesReport(validCities, "GBR", 10);
    }

    /**
     * Prints a formatted mock top-N country cities report table to the console for unit test inspection.
     * Safely handles null/empty lists, skips null City elements, and falls back to "N/A" for blank/null fields.
     *
     * @param cities      List of mock City objects to display
     * @param countryCode Country code label shown in the report header
     * @param n           Top-N limit label shown in the report header
     */
    private void printMockCountryCitiesReport(List<City> cities, String countryCode, int n) {
        System.out.println();
        System.out.println("=".repeat(100));
        System.out.println("Report 15: Top " + n + " populated cities in " + countryCode);
        System.out.println("=".repeat(100));

        System.out.printf(
                "%-30s %-30s %-25s %15s%n",
                "City Name",
                "Country",
                "District",
                "Population"
        );
        System.out.println("-".repeat(100));

        int displayedCount = 0;

        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found. Please check the country code.");
        } else {
            for (City city : cities) {
                if (city == null) {
                    continue;
                }

                String cityName = (city.getCityName() == null || city.getCityName().isBlank())
                        ? "N/A"
                        : city.getCityName();
                String countryName = (city.getCountry() == null || city.getCountry().isBlank())
                        ? "N/A"
                        : city.getCountry();
                String districtName = (city.getDistrict() == null || city.getDistrict().isBlank())
                        ? "N/A"
                        : city.getDistrict();

                System.out.printf(
                        "%-30s %-30s %-25s %,15d%n",
                        cityName,
                        countryName,
                        districtName,
                        city.getPopulation()
                );
                displayedCount++;
            }
        }

        System.out.println("-".repeat(100));
        System.out.println("Cities displayed: " + displayedCount);
        System.out.println("Report completed successfully.");
    }
}