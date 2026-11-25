package com.globalpop.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests for the PopulationLivingInVsOutOfCities model.
 * It checks if data stores correctly and if percentage calculations are accurate.
 * It also prints messages to the screen to show the results.
 */
public class PopulationLivingInVsOutOfCitiesTest {

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("     RUNNING UNIT TESTS FOR POPULATION LIVING IN VS OUT CITIES    ");
        System.out.println("******************************************************************\n");
    }

    /**
     * Tests the parameterized constructor and the basic getter methods.
     * Creates an object with data already inside, prints the data, and checks for accuracy.
     */
    @Test
    void testConstructorAndGetters() {
        // Create a new object and put data directly inside the parentheses
        PopulationLivingInVsOutOfCities popData = new PopulationLivingInVsOutOfCities(
                "North America", 600000000L, 450000000L, 150000000L);

        // Print the starting data to the screen
        System.out.println("--- Testing Constructor and Basic Getters ---");
        System.out.println("Expected Name: North America | Actual: " + popData.getName());
        System.out.println("Expected Total Population: 600000000 | Actual: " + popData.getTotalPopulation());
        System.out.println("Expected Urban Population: 450000000 | Actual: " + popData.getUrbanPopulation());
        System.out.println("Expected Rural Population: 150000000 | Actual: " + popData.getRuralPopulation());

        // Check if the getter methods return the correct starting data
        assertEquals("North America", popData.getName(), "The name should be North America.");
        assertEquals(600000000L, popData.getTotalPopulation(), "The total population should be 600000000.");
        assertEquals(450000000L, popData.getUrbanPopulation(), "The urban population should be 450000000.");
        assertEquals(150000000L, popData.getRuralPopulation(), "The rural population should be 150000000.");

        System.out.println("Result: Constructor and Getters Test Passed!\n");
    }

    /**
     * Tests the calculation methods for urban and rural percentages.
     * Creates an object, calculates the percentages, prints them, and checks the math logic.
     */
    @Test
    void testPercentageCalculations() {
        // Create a new object with specific numbers to test math logic
        PopulationLivingInVsOutOfCities popData = new PopulationLivingInVsOutOfCities(
                "Oceania", 40000000L, 30000000L, 10000000L);

        // Print the calculated percentages to the screen
        System.out.println("--- Testing Percentage Calculations ---");
        System.out.println("Expected Urban Percentage: 75.0 | Actual: " + popData.getUrbanPercentage());
        System.out.println("Expected Rural Percentage: 25.0 | Actual: " + popData.getRuralPercentage());

        // Check if the calculation methods return the correct percentages
        // The 0.001 value allows a tiny margin of error for decimal numbers
        assertEquals(75.0, popData.getUrbanPercentage(), 0.001, "The urban percentage should be exactly 75.0.");
        assertEquals(25.0, popData.getRuralPercentage(), 0.001, "The rural percentage should be exactly 25.0.");

        System.out.println("Result: Percentage Calculations Test Passed!\n");
    }

    /**
     * Tests the percentage calculations when the total population is zero.
     * This ensures the program does not crash when trying to divide by zero.
     */
    @Test
    void testZeroTotalPopulation() {
        // Create an object where total population is exactly zero
        PopulationLivingInVsOutOfCities popData = new PopulationLivingInVsOutOfCities(
                "Empty Region", 0L, 0L, 0L);

        // Print the zero-case percentages to the screen
        System.out.println("--- Testing Zero Total Population Case ---");
        System.out.println("Expected Urban Percentage: 0.0 | Actual: " + popData.getUrbanPercentage());
        System.out.println("Expected Rural Percentage: 0.0 | Actual: " + popData.getRuralPercentage());

        // Check if the methods safely return 0.0 without mathematical errors
        assertEquals(0.0, popData.getUrbanPercentage(), 0.001, "The urban percentage should safely return 0.0 when total is zero.");
        assertEquals(0.0, popData.getRuralPercentage(), 0.001, "The rural percentage should safely return 0.0 when total is zero.");

        System.out.println("Result: Zero Total Population Test Passed!\n");
    }

    /**
     * Tests the toString method.
     * Creates an object, generates the string output, prints the text, and checks for exact formatting.
     */
    @Test
    void testToString() {
        // Create a new object with data
        PopulationLivingInVsOutOfCities popData = new PopulationLivingInVsOutOfCities(
                "Europe", 740000000L, 550000000L, 190000000L);

        // Call the toString method to get the formatted text
        String actualString = popData.toString();

        // Define the exact text format expected from the method
        String expectedString = "PopulationLivingInVsOutOfCities{name='Europe', totalPopulation=740000000, urbanPopulation=550000000, ruralPopulation=190000000}";

        // Print the string texts to the screen
        System.out.println("--- Testing toString Method ---");
        System.out.println("Expected String: " + expectedString);
        System.out.println("Actual String:   " + actualString);

        // Check if the actual string matches the expected string perfectly
        assertEquals(expectedString, actualString, "The toString method should format the text exactly as expected.");

        System.out.println("Result: toString Test Passed!\n");
    }
}