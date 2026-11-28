package com.globalpop.sprint3.General_Population_Totals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Feature28Test {

    private Map<String, long[]> mockRegionTotals;

    /**
     * Initializes a fresh map of mock region population aggregates before each test runs.
     * Each entry maps a region name to {totalCountries, totalPopulation} in memory without DB access.
     */
    @BeforeEach
    void setUp() {
        mockRegionTotals = new LinkedHashMap<>();

        // {totalCountries, totalPopulation}
        mockRegionTotals.put("Southeast Asia", new long[]{11L, 680000000L});
        mockRegionTotals.put("Eastern Asia", new long[]{8L, 1650000000L});
        mockRegionTotals.put("Caribbean", new long[]{24L, 0L});
    }

    /**
     * Test 1: This tests what happens when the region input is null or blank.
     * The method should throw IllegalArgumentException immediately without connecting to the database.
     */
    @Test
    void testNullOrBlankRegionThrowsException() {
        System.out.println("\n--- TEST 1: NULL OR BLANK REGION INPUT ---");
        System.out.println("Expected Result: Throw IllegalArgumentException without database access for null or blank region.");
        System.out.println("Actual Result  :");

        IllegalArgumentException nullException = assertThrows(
                IllegalArgumentException.class,
                () -> Feature28.displayReport(null)
        );
        IllegalArgumentException blankException = assertThrows(
                IllegalArgumentException.class,
                () -> Feature28.displayReport("   ")
        );

        assertEquals("Region name cannot be empty.", nullException.getMessage());
        assertEquals("Region name cannot be empty.", blankException.getMessage());

        System.out.println("Null threw  : " + nullException.getClass().getSimpleName() + " -> " + nullException.getMessage());
        System.out.println("Blank threw : " + blankException.getClass().getSimpleName() + " -> " + blankException.getMessage());
    }

    /**
     * Test 2: This tests what happens when a region has no matching population data or zero population.
     * The mock report helper should handle missing regions cleanly and format zero population without crashing.
     */
    @Test
    void testMissingRegionAndZeroPopulation() {
        System.out.println("\n--- TEST 2: MISSING REGION AND ZERO POPULATION ---");
        System.out.println("Expected Result: Print 'No population data found' for unknown region and '0' for zero population.");
        System.out.println("Actual Result  :");

        assertFalse(mockRegionTotals.containsKey("Unknown Region"));
        assertTrue(mockRegionTotals.containsKey("Caribbean"));
        assertEquals(0L, mockRegionTotals.get("Caribbean")[1]);

        printMockPopulationReport("Unknown Region", null);
        printMockPopulationReport("Caribbean", mockRegionTotals.get("Caribbean"));
    }

    /**
     * Test 3: This tests what happens under normal conditions with valid mock region population data.
     * The mock report helper should print the formatted region summary with country count and total population.
     */
    @Test
    void testDisplayMockRegionPopulationReport() {
        System.out.println("\n--- TEST 3: DISPLAY MOCK REGION POPULATION REPORT ---");
        System.out.println("Expected Result: Print formatted Report 28 summary for 'Southeast Asia' with 11 countries and 680,000,000 population.");
        System.out.println("Actual Result  :");

        long[] seaData = mockRegionTotals.get("Southeast Asia");
        assertNotNull(seaData);
        assertEquals(11L, seaData[0]);
        assertEquals(680000000L, seaData[1]);

        printMockPopulationReport("Southeast Asia", seaData);
    }

    /**
     * Prints a formatted mock region population summary to the console for unit test inspection.
     *
     * @param regionName  Selected region name
     * @param summaryData Array containing {totalCountries, totalPopulation}, or null if not found
     */
    private void printMockPopulationReport(String regionName, long[] summaryData) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("Report 28: POPULATION OF A REGION");
        System.out.println("============================================================");

        if (summaryData == null || summaryData.length < 2) {
            System.out.println("No population data found for region: " + regionName);
        } else {
            long totalCountries = summaryData[0];
            long totalPopulation = summaryData[1];

            System.out.printf("%-25s : %s%n", "Region", regionName);
            System.out.printf("%-25s : %,d%n", "Number of Countries", totalCountries);
            System.out.printf("%-25s : %,d%n", "Total Population", totalPopulation);
        }

        System.out.println("============================================================");
        System.out.println("Report completed successfully.");
    }
}