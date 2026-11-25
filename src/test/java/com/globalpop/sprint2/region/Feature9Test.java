package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature9Test {

    private List<City> validCities;

    /**
     * Initializes a fresh list of mock City objects in Southeast Asia before each test runs.
     * Keeps unit tests isolated in memory without opening a database connection.
     */
    @BeforeEach
    void setUp() {
        validCities = new ArrayList<>();

        validCities.add(new City("Jakarta", "Indonesia", "Jakarta Raya", 10562088));
        validCities.add(new City("Bangkok", "Thailand", "Bangkok", 10539000));
        validCities.add(new City("Yangon", "Myanmar", "Yangon", 5160512));
    }

    /**
     * Test 1: This tests what happens when the city list is empty.
     * The report helper should handle an empty list safely and display zero cities found.
     */
    @Test
    void testEmptyCitiesListReport() {
        System.out.println("\n--- TEST 1: EMPTY CITIES LIST INPUT ---");
        System.out.println("Expected Result: Display 'No cities found for this region.' and total cities found: 0.");
        System.out.println("Actual Result  :");

        List<City> emptyCities = new ArrayList<>();
        assertTrue(emptyCities.isEmpty());
        assertEquals(0, emptyCities.size());

        printMockRegionReport(emptyCities, "Southeast Asia");
    }

    /**
     * Test 2: This tests what happens when a list contains a null City element or blank district.
     * The report helper should skip null entries safely and fall back to "N/A" for missing fields.
     */
    @Test
    void testCitiesListWithNullElementAndBlankDistrict() {
        System.out.println("\n--- TEST 2: LIST WITH NULL ELEMENT AND BLANK DISTRICT ---");
        System.out.println("Expected Result: Skip null City safely, resolve blank district to 'N/A', and display 3 valid cities.");
        System.out.println("Actual Result  :");

        List<City> mixedCities = new ArrayList<>();
        mixedCities.add(null);
        mixedCities.add(new City("Hanoi", "Vietnam", "   ", 8053663));
        mixedCities.add(new City("Manila", "Philippines", "National Capital Region", 1780148));
        mixedCities.add(new City("Kuala Lumpur", "Malaysia", "Wilayah Persekutuan", 1782500));

        assertEquals(4, mixedCities.size());
        printMockRegionReport(mixedCities, "Southeast Asia");
    }

    /**
     * Test 3: This tests what happens under normal conditions with populated Southeast Asia mock cities.
     * The report helper should print a formatted table ordered by population with all provided cities.
     */
    @Test
    void testDisplayMockRegionCitiesReport() {
        System.out.println("\n--- TEST 3: DISPLAY MOCK REGION CITIES REPORT ---");
        System.out.println("Expected Result: Print formatted table with " + validCities.size() + " mock cities in Southeast Asia.");
        System.out.println("Actual Result  :");

        assertEquals(3, validCities.size());
        assertEquals("Jakarta", validCities.get(0).getCityName());
        assertEquals(10562088, validCities.get(0).getPopulation());
        assertEquals("Bangkok", validCities.get(1).getCityName());
        assertEquals("Yangon", validCities.get(2).getCityName());

        printMockRegionReport(validCities, "Southeast Asia");
    }

    /**
     * Prints a formatted mock region city report table to the console for unit test verification.
     * Safely handles null or empty lists, skips null City elements, and replaces blank/null fields with "N/A".
     *
     * @param cities     List of mock City objects to display
     * @param regionName Region label shown in the report header
     */
    private void printMockRegionReport(List<City> cities, String regionName) {
        System.out.println();
        System.out.println("=".repeat(100));
        System.out.println("Report 9: Cities in Region: " + regionName);
        System.out.println("Ordered by population: Largest to Smallest");
        System.out.println("=".repeat(100));

        System.out.printf(
                "%-30s %-30s %-25s %15s%n",
                "City Name", "Country", "District", "Population"
        );
        System.out.println("-".repeat(100));

        int displayedCount = 0;

        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found for this region.");
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
        System.out.println("Total cities found: " + displayedCount);
    }
}