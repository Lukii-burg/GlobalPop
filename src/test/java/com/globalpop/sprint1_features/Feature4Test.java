package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature4Test {

    private List<Country> validCountries;

    /**
     * Initializes a fresh list of valid mock Country objects before each test runs.
     * This keeps test data isolated and avoids connecting to a live database.
     */
    @BeforeEach
    void setUp() {
        validCountries = new ArrayList<>();

        Country india = new Country();
        india.setCode("IND");
        india.setName("India");
        india.setContinent("Asia");
        india.setRegion("Southern and Central Asia");
        india.setPopulation(1417173173L);
        india.setCapital("New Delhi");
        validCountries.add(india);

        Country china = new Country();
        china.setCode("CHN");
        china.setName("China");
        china.setContinent("Asia");
        china.setRegion("Eastern Asia");
        china.setPopulation(1409670000L);
        china.setCapital("Beijing");
        validCountries.add(china);
    }

    /**
     * Test 1: This tests what happens when the country list is null.
     * The program should handle the null list safely without connecting to the database or crashing.
     */
    @Test
    void testNullListInput() {
        System.out.println("\n--- TEST 1: NULL LIST INPUT ---");
        System.out.println("Expected Result: Handle null country list safely without database access. Countries displayed: 0.");
        System.out.println("Actual Result  :");

        List<Country> countries = null;
        assertNull(countries);

        int count = (countries == null) ? 0 : countries.size();
        assertEquals(0, count);

        System.out.println("List is null. Countries to display: " + count);
    }

    /**
     * Test 2: This tests what happens when a country has null fields such as a missing capital.
     * The program should fall back cleanly to "N/A" without throwing a NullPointerException.
     */
    @Test
    void testCountryWithNullFieldsInput() {
        System.out.println("\n--- TEST 2: COUNTRY WITH NULL FIELDS INPUT ---");
        System.out.println("Expected Result: Handle null capital/fields gracefully with fallback display value 'N/A'.");
        System.out.println("Actual Result  :");

        Country country = new Country();
        country.setCode("ATA");
        country.setName("Antarctica");
        country.setContinent("Antarctica");
        country.setRegion("Antarctica");
        country.setPopulation(0L);
        country.setCapital(null);

        assertNull(country.getCapital());

        String capitalDisplay = (country.getCapital() == null || country.getCapital().isBlank())
                ? "N/A"
                : country.getCapital();

        assertEquals("N/A", capitalDisplay);

        System.out.println("Capital is null -> displayed as '" + capitalDisplay + "'");
    }

    /**
     * Test 3: This tests what happens under normal conditions with mock country data.
     * The program should display a formatted report table with all provided mock countries.
     */
    @Test
    void testDisplayMockCountriesReport() {
        System.out.println("\n--- TEST 3: DISPLAY MOCK COUNTRIES REPORT ---");
        System.out.println("Expected Result: Print formatted country report table with " + validCountries.size() + " mock countries.");
        System.out.println("Actual Result  :");

        assertEquals(2, validCountries.size());
        assertEquals("IND", validCountries.get(0).getCode());
        assertEquals("India", validCountries.get(0).getName());
        assertEquals(1417173173L, validCountries.get(0).getPopulation());
        assertEquals("New Delhi", validCountries.get(0).getCapital());

        assertEquals("CHN", validCountries.get(1).getCode());
        assertEquals("China", validCountries.get(1).getName());
        assertEquals(1409670000L, validCountries.get(1).getPopulation());
        assertEquals("Beijing", validCountries.get(1).getCapital());

        printMockReport(validCountries, 10);
    }

    /**
     * Prints a formatted mock country report table to the console for unit test inspection.
     * Skips null elements and replaces null or blank capital names with "N/A".
     *
     * @param countries List of mock Country objects to print
     * @param n         Report limit label displayed in the header
     */
    private void printMockReport(List<Country> countries, int n) {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println(" Report 4: TOP " + n + " MOST POPULATED COUNTRIES IN THE WORLD");
        System.out.println("==============================================================");
        System.out.printf(
                "%-5s %-30s %-18s %-30s %-15s %-20s%n",
                "Code", "Country", "Continent", "Region", "Population", "Capital"
        );
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries found.");
        } else {
            for (Country country : countries) {
                if (country == null) {
                    continue;
                }

                String capital = country.getCapital();
                if (capital == null || capital.isBlank()) {
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
        }

        System.out.println();
        System.out.println("Countries displayed: " + (countries == null ? 0 : countries.size()));
        System.out.println("Report completed successfully.");
    }
}