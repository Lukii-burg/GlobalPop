package com.globalpop.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests for the CapitalCity model.
 * It checks if data stores and retrieves correctly.
 * It also prints messages to the screen to show the results.
 */
public class CapitalCityTest {

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("               RUNNING UNIT TESTS FOR CAPITAL CITY                ");
        System.out.println("******************************************************************\n");
    }

    /**
     * Tests the empty default constructor and the setter methods.
     * Creates an empty object, adds data, prints the data, and checks for accuracy.
     */
    @Test
    void testDefaultConstructorAndSetters() {
        // Create a new empty CapitalCity object
        CapitalCity city = new CapitalCity();

        // Add data using the setter methods
        city.setCapitalCityName("London");
        city.setCountry("United Kingdom");
        city.setPopulation(8982000);

        // Print the added data to the screen
        System.out.println("--- Testing Default Constructor and Setters ---");
        System.out.println("Expected Capital City Name: London | Actual: " + city.getCapitalCityName());
        System.out.println("Expected Country: United Kingdom | Actual: " + city.getCountry());
        System.out.println("Expected Population: 8982000 | Actual: " + city.getPopulation());

        // Check if the getter methods return the correct added data
        assertEquals("London", city.getCapitalCityName(), "The capital city name should be London.");
        assertEquals("United Kingdom", city.getCountry(), "The country name should be United Kingdom.");
        assertEquals(8982000, city.getPopulation(), "The population should be 8982000.");

        System.out.println("Result: Default Constructor and Setters Test Passed!\n");
    }

    /**
     * Tests the parameterized constructor.
     * Creates an object with data already inside, prints the data, and checks for accuracy.
     */
    @Test
    void testParameterizedConstructor() {
        // Create a new CapitalCity object and put data directly inside the parentheses
        CapitalCity city = new CapitalCity("Tokyo", "Japan", 13929286);

        // Print the starting data to the screen
        System.out.println("--- Testing Parameterized Constructor ---");
        System.out.println("Expected Capital City Name: Tokyo | Actual: " + city.getCapitalCityName());
        System.out.println("Expected Country: Japan | Actual: " + city.getCountry());
        System.out.println("Expected Population: 13929286 | Actual: " + city.getPopulation());

        // Check if the getter methods return the correct starting data
        assertEquals("Tokyo", city.getCapitalCityName(), "The capital city name should be Tokyo.");
        assertEquals("Japan", city.getCountry(), "The country name should be Japan.");
        assertEquals(13929286, city.getPopulation(), "The population should be 13929286.");

        System.out.println("Result: Parameterized Constructor Test Passed!\n");
    }
}