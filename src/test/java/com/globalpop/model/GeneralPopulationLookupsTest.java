package com.globalpop.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests for the GeneralPopulationLookups model.
 * It checks if data stores and retrieves correctly.
 * It also prints messages to the screen to show the results.
 */
public class GeneralPopulationLookupsTest {

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("       RUNNING UNIT TESTS FOR GENERAL POPULATION LOOKUPS          ");
        System.out.println("******************************************************************\n");
    }

    /**
     * Tests the empty default constructor and the setter methods.
     * Creates an empty object, adds data, prints the data, and checks for accuracy.
     */
    @Test
    void testDefaultConstructorAndSetters() {
        // Create a new empty GeneralPopulationLookups object
        GeneralPopulationLookups lookup = new GeneralPopulationLookups();

        // Add data using the setter methods
        // Notice the 'L' after the population number because it is a long data type
        lookup.setName("World");
        lookup.setPopulation(8000000000L);

        // Print the added data to the screen
        System.out.println("--- Testing Default Constructor and Setters ---");
        System.out.println("Expected Name: World | Actual: " + lookup.getName());
        System.out.println("Expected Population: 8000000000 | Actual: " + lookup.getPopulation());

        // Check if the getter methods return the correct added data
        assertEquals("World", lookup.getName(), "The name should be World.");
        assertEquals(8000000000L, lookup.getPopulation(), "The population should be 8000000000.");

        System.out.println("Result: Default Constructor and Setters Test Passed!\n");
    }

    /**
     * Tests the parameterized constructor.
     * Creates an object with data already inside, prints the data, and checks for accuracy.
     */
    @Test
    void testParameterizedConstructor() {
        // Create a new GeneralPopulationLookups object and put data directly inside the parentheses
        GeneralPopulationLookups lookup = new GeneralPopulationLookups("Asia", 4700000000L);

        // Print the starting data to the screen
        System.out.println("--- Testing Parameterized Constructor ---");
        System.out.println("Expected Name: Asia | Actual: " + lookup.getName());
        System.out.println("Expected Population: 4700000000 | Actual: " + lookup.getPopulation());

        // Check if the getter methods return the correct starting data
        assertEquals("Asia", lookup.getName(), "The name should be Asia.");
        assertEquals(4700000000L, lookup.getPopulation(), "The population should be 4700000000.");

        System.out.println("Result: Parameterized Constructor Test Passed!\n");
    }

    /**
     * Tests the toString method.
     * Creates an object, generates the string output, prints the text, and checks for exact formatting.
     */
    @Test
    void testToString() {
        // Create a new GeneralPopulationLookups object with data
        GeneralPopulationLookups lookup = new GeneralPopulationLookups("Europe", 746000000L);

        // Call the toString method to get the formatted text
        String actualString = lookup.toString();

        // Define the exact text format expected from the method
        String expectedString = "GeneralPopulationLookups{name='Europe', population=746000000}";

        // Print the string texts to the screen
        System.out.println("--- Testing toString Method ---");
        System.out.println("Expected String: " + expectedString);
        System.out.println("Actual String:   " + actualString);

        // Check if the actual string matches the expected string perfectly
        assertEquals(expectedString, actualString, "The toString method should format the text exactly as expected.");

        System.out.println("Result: toString Test Passed!\n");
    }
}