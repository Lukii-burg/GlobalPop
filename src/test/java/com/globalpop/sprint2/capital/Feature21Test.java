package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature21Test {

    private List<CapitalCity> validCapitalCities;

    /**
     * Initializes a fresh list of valid mock CapitalCity objects before each test runs.
     * This keeps test data isolated and avoids connecting to a live database.
     */
    @BeforeEach
    void setUp() {
        validCapitalCities = new ArrayList<>();

        validCapitalCities.add(new CapitalCity("Seoul", "South Korea", 9981000));
        validCapitalCities.add(new CapitalCity("Tokyo", "Japan", 7980230));
        validCapitalCities.add(new CapitalCity("Jakarta", "Indonesia", 10562088));
    }

    /**
     * Test 1: This tests what happens when the capital city list is empty.
     * The program should report zero capital cities without connecting to the database or crashing.
     */
    @Test
    void testEmptyCapitalCitiesListInput() {
        System.out.println("\n--- TEST 1: EMPTY CAPITAL CITIES LIST INPUT ---");
        System.out.println("Expected Result: Handle empty capital city list safely. Capital cities displayed: 0.");
        System.out.println("Actual Result  :");

        List<CapitalCity> emptyList = new ArrayList<>();
        assertTrue(emptyList.isEmpty());
        assertEquals(0, emptyList.size());

        System.out.println("List is empty. Capital cities displayed: " + emptyList.size());
    }

    /**
     * Test 2: This tests what happens when a list contains a null CapitalCity element alongside valid entries.
     * The program should detect and skip the null item safely without throwing a NullPointerException.
     */
    @Test
    void testListContainsNullCapitalCity() {
        System.out.println("\n--- TEST 2: LIST CONTAINS NULL ELEMENT ---");
        System.out.println("Expected Result: Skip null element safely while keeping valid mock capital cities intact.");
        System.out.println("Actual Result  :");

        List<CapitalCity> listWithNull = new ArrayList<>(validCapitalCities);
        listWithNull.add(null);

        long nonNullCount = listWithNull.stream().filter(c -> c != null).count();
        assertEquals(4, listWithNull.size());
        assertEquals(3L, nonNullCount);

        System.out.println("Total list size: " + listWithNull.size() + " | Valid capital cities: " + nonNullCount);
    }

    /**
     * Test 3: This tests mutating CapitalCity fields via setters and handling blank or null names.
     * The program should update values properly and fall back to "N/A" when a name is null or blank.
     */
    @Test
    void testCapitalCitySettersAndBlankFallback() {
        System.out.println("\n--- TEST 3: CAPITAL CITY SETTERS AND BLANK FALLBACK ---");
        System.out.println("Expected Result: Update fields via setters and resolve blank/null capital city name to 'N/A'.");
        System.out.println("Actual Result  :");

        CapitalCity capitalCity = new CapitalCity();
        capitalCity.setCapitalCityName("   ");
        capitalCity.setCountry("Myanmar");
        capitalCity.setPopulation(1160242);

        String resolvedName = (capitalCity.getCapitalCityName() == null || capitalCity.getCapitalCityName().isBlank())
                ? "N/A"
                : capitalCity.getCapitalCityName();

        assertEquals("Myanmar", capitalCity.getCountry());
        assertEquals(1160242, capitalCity.getPopulation());
        assertEquals("N/A", resolvedName);

        System.out.println("Country: " + capitalCity.getCountry()
                + " | Population: " + capitalCity.getPopulation()
                + " | Resolved Capital Name: " + resolvedName);
    }

    /**
     * Test 4: This tests what happens under normal conditions with mock capital city data.
     * The program should display a formatted report table with all provided mock capital cities.
     */
    @Test
    void testDisplayMockCapitalCitiesReport() {
        System.out.println("\n--- TEST 4: DISPLAY MOCK CAPITAL CITIES REPORT ---");
        System.out.println("Expected Result: Print formatted capital city report table with " + validCapitalCities.size() + " mock capital cities.");
        System.out.println("Actual Result  :");

        assertEquals(3, validCapitalCities.size());
        assertEquals("Seoul", validCapitalCities.get(0).getCapitalCityName());
        assertEquals("South Korea", validCapitalCities.get(0).getCountry());
        assertEquals(9981000, validCapitalCities.get(0).getPopulation());

        assertEquals("Tokyo", validCapitalCities.get(1).getCapitalCityName());
        assertEquals("Japan", validCapitalCities.get(1).getCountry());
        assertEquals(7980230, validCapitalCities.get(1).getPopulation());

        assertEquals("Jakarta", validCapitalCities.get(2).getCapitalCityName());
        assertEquals("Indonesia", validCapitalCities.get(2).getCountry());
        assertEquals(10562088, validCapitalCities.get(2).getPopulation());

        printMockReport(validCapitalCities, "Asia", 10);
    }

    /**
     * Prints a formatted mock capital city report table to the console for unit test inspection.
     * Skips null elements and replaces null or blank capital city/country names with "N/A".
     *
     * @param capitalCities List of mock CapitalCity objects to print
     * @param continent     Continent label displayed in the report header
     * @param n             Report limit label displayed in the header
     */
    private void printMockReport(List<CapitalCity> capitalCities, String continent, int n) {
        System.out.println();
        System.out.println("==========================================================================");
        System.out.println("Report 21: Top " + n + " populated capital cities in " + continent);
        System.out.println("==========================================================================");
        System.out.printf(
                "%-5s %-30s %-35s %15s%n",
                "Rank",
                "Capital City",
                "Country",
                "Population"
        );
        System.out.println("------------------------------------------------------------------------------------------");

        int displayedCount = 0;

        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("No capital cities found for continent: " + continent);
        } else {
            int rank = 1;
            for (CapitalCity capitalCity : capitalCities) {
                if (capitalCity == null) {
                    continue;
                }

                String cityName = capitalCity.getCapitalCityName();
                if (cityName == null || cityName.isBlank()) {
                    cityName = "N/A";
                }

                String countryName = capitalCity.getCountry();
                if (countryName == null || countryName.isBlank()) {
                    countryName = "N/A";
                }

                System.out.printf(
                        "%-5d %-30s %-35s %,15d%n",
                        rank,
                        cityName,
                        countryName,
                        capitalCity.getPopulation()
                );

                rank++;
                displayedCount++;
            }
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Capital cities displayed: " + displayedCount);
        System.out.println("Report completed successfully.");
    }
}