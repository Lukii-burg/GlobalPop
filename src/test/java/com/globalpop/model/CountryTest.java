package com.globalpop.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests for the Country model.
 * It checks if data stores and retrieves correctly.
 * It also prints messages to the screen to show the results.
 */
public class CountryTest {

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                 RUNNING UNIT TESTS FOR COUNTRY                   ");
        System.out.println("******************************************************************\n");
    }

    /**
     * Tests the empty default constructor and the setter methods.
     * Creates an empty object, adds data, prints the data, and checks for accuracy.
     */
    @Test
    void testDefaultConstructorAndSetters() {
        // Create a new empty Country object
        Country country = new Country();

        // Add data using the setter methods
        // Notice the 'L' after the population number because it is a long data type
        country.setCode("MMR");
        country.setName("Myanmar");
        country.setContinent("Asia");
        country.setRegion("Southeast Asia");
        country.setPopulation(54409800L);
        country.setCapital("Naypyidaw");

        // Print the added data to the screen
        System.out.println("--- Testing Default Constructor and Setters ---");
        System.out.println("Expected Code: MMR | Actual: " + country.getCode());
        System.out.println("Expected Name: Myanmar | Actual: " + country.getName());
        System.out.println("Expected Continent: Asia | Actual: " + country.getContinent());
        System.out.println("Expected Region: Southeast Asia | Actual: " + country.getRegion());
        System.out.println("Expected Population: 54409800 | Actual: " + country.getPopulation());
        System.out.println("Expected Capital: Naypyidaw | Actual: " + country.getCapital());

        // Check if the getter methods return the correct added data
        assertEquals("MMR", country.getCode(), "The country code should be MMR.");
        assertEquals("Myanmar", country.getName(), "The country name should be Myanmar.");
        assertEquals("Asia", country.getContinent(), "The continent should be Asia.");
        assertEquals("Southeast Asia", country.getRegion(), "The region should be Southeast Asia.");
        assertEquals(54409800L, country.getPopulation(), "The population should be 54409800.");
        assertEquals("Naypyidaw", country.getCapital(), "The capital should be Naypyidaw.");

        System.out.println("Result: Default Constructor and Setters Test Passed!\n");
    }

    /**
     * Tests the parameterized constructor.
     * Creates an object with data already inside, prints the data, and checks for accuracy.
     */
    @Test
    void testParameterizedConstructor() {
        // Create a new Country object and put data directly inside the parentheses
        Country country = new Country("JPN", "Japan", "Asia", "Eastern Asia", 125800000L, "Tokyo");

        // Print the starting data to the screen
        System.out.println("--- Testing Parameterized Constructor ---");
        System.out.println("Expected Code: JPN | Actual: " + country.getCode());
        System.out.println("Expected Name: Japan | Actual: " + country.getName());
        System.out.println("Expected Continent: Asia | Actual: " + country.getContinent());
        System.out.println("Expected Region: Eastern Asia | Actual: " + country.getRegion());
        System.out.println("Expected Population: 125800000 | Actual: " + country.getPopulation());
        System.out.println("Expected Capital: Tokyo | Actual: " + country.getCapital());

        // Check if the getter methods return the correct starting data
        assertEquals("JPN", country.getCode(), "The country code should be JPN.");
        assertEquals("Japan", country.getName(), "The country name should be Japan.");
        assertEquals("Asia", country.getContinent(), "The continent should be Asia.");
        assertEquals("Eastern Asia", country.getRegion(), "The region should be Eastern Asia.");
        assertEquals(125800000L, country.getPopulation(), "The population should be 125800000.");
        assertEquals("Tokyo", country.getCapital(), "The capital should be Tokyo.");

        System.out.println("Result: Parameterized Constructor Test Passed!\n");
    }
}