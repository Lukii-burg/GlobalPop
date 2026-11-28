package com.globalpop.sprint3.General_Population_Totals;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Feature29Test {

    private Map<String, Country> mockCountries;

    /**
     * Initializes a fresh map of mock Country objects before each test runs.
     * Keeps unit tests isolated in memory without opening a database connection.
     */
    @BeforeEach
    void setUp() {
        mockCountries = new LinkedHashMap<>();

        Country myanmar = new Country();
        myanmar.setCode("MMR");
        myanmar.setName("Myanmar");
        myanmar.setContinent("Asia");
        myanmar.setRegion("Southeast Asia");
        myanmar.setPopulation(54409800L);
        mockCountries.put("MMR", myanmar);

        Country japan = new Country();
        japan.setCode("JPN");
        japan.setName("Japan");
        japan.setContinent("Asia");
        japan.setRegion("Eastern Asia");
        japan.setPopulation(123951692L);
        mockCountries.put("JPN", japan);
    }

    /**
     * Test 1: This tests what happens when the country code input is null or blank.
     * The method should throw IllegalArgumentException immediately without connecting to the database.
     */
    @Test
    void testNullOrBlankCountryCodeThrowsException() {
        System.out.println("\n--- TEST 1: NULL OR BLANK COUNTRY CODE INPUT ---");
        System.out.println("Expected Result: Throw IllegalArgumentException without database access for null or blank country code.");
        System.out.println("Actual Result  :");

        IllegalArgumentException nullException = assertThrows(
                IllegalArgumentException.class,
                () -> Feature29.displayReport(null)
        );
        IllegalArgumentException blankException = assertThrows(
                IllegalArgumentException.class,
                () -> Feature29.displayReport("   ")
        );

        assertEquals("Country code cannot be empty.", nullException.getMessage());
        assertEquals("Country code cannot be empty.", blankException.getMessage());

        System.out.println("Null threw  : " + nullException.getClass().getSimpleName() + " -> " + nullException.getMessage());
        System.out.println("Blank threw : " + blankException.getClass().getSimpleName() + " -> " + blankException.getMessage());
    }

    /**
     * Test 2: This tests what happens when a country code is not found or has null/blank fields.
     * The mock report helper should report no country found for missing codes and fall back to "N/A" for blank fields.
     */
    @Test
    void testMissingCountryAndBlankFields() {
        System.out.println("\n--- TEST 2: MISSING COUNTRY AND BLANK FIELDS ---");
        System.out.println("Expected Result: Print 'No country found with code: XXX' for unknown code and 'N/A' for missing fields.");
        System.out.println("Actual Result  :");

        assertFalse(mockCountries.containsKey("XXX"));

        Country partialCountry = new Country();
        partialCountry.setCode("ATA");
        partialCountry.setName("Antarctica");
        partialCountry.setContinent(null);
        partialCountry.setRegion("   ");
        partialCountry.setPopulation(0L);

        printMockCountryPopulationReport("XXX", null);
        printMockCountryPopulationReport("ATA", partialCountry);
    }

    /**
     * Test 3: This tests what happens under normal conditions with valid mock country data.
     * The mock report helper should print the formatted Report 29 summary with code, name, continent, region, and population.
     */
    @Test
    void testDisplayMockCountryPopulationReport() {
        System.out.println("\n--- TEST 3: DISPLAY MOCK COUNTRY POPULATION REPORT ---");
        System.out.println("Expected Result: Print formatted Report 29 summary for 'MMR' (Myanmar).");
        System.out.println("Actual Result  :");

        Country mmr = mockCountries.get("MMR");
        assertNotNull(mmr);
        assertEquals("MMR", mmr.getCode());
        assertEquals("Myanmar", mmr.getName());
        assertEquals("Asia", mmr.getContinent());
        assertEquals("Southeast Asia", mmr.getRegion());
        assertEquals(54409800L, mmr.getPopulation());

        printMockCountryPopulationReport("MMR", mmr);
    }

    /**
     * Prints a formatted mock country population report to the console for unit test inspection.
     * Safely handles null countries and falls back to "N/A" for null or blank text fields.
     *
     * @param countryCode Requested country code
     * @param country     Mock Country object to display, or null if not found
     */
    private void printMockCountryPopulationReport(String countryCode, Country country) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("Report 29: POPULATION OF A COUNTRY");
        System.out.println("============================================================");

        if (country == null) {
            System.out.println("No country found with code: " + countryCode);
        } else {
            String code = (country.getCode() == null || country.getCode().isBlank()) ? "N/A" : country.getCode().toUpperCase();
            String name = (country.getName() == null || country.getName().isBlank()) ? "N/A" : country.getName();
            String continent = (country.getContinent() == null || country.getContinent().isBlank()) ? "N/A" : country.getContinent();
            String region = (country.getRegion() == null || country.getRegion().isBlank()) ? "N/A" : country.getRegion();

            System.out.printf("%-20s : %s%n", "Country Code", code);
            System.out.printf("%-20s : %s%n", "Country Name", name);
            System.out.printf("%-20s : %s%n", "Continent", continent);
            System.out.printf("%-20s : %s%n", "Region", region);
            System.out.printf("%-20s : %,d%n", "Total Population", country.getPopulation());
        }

        System.out.println("============================================================");
        System.out.println("Report completed successfully.");
    }
}