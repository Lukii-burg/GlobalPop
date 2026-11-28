package com.globalpop.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests for the City model.
 * It checks if data stores and retrieves correctly.
 * It also prints messages to the screen to show the results.
 */
public class CityTest {

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                   RUNNING UNIT TESTS FOR CITY                    ");
        System.out.println("******************************************************************\n");
    }

    /**
     * Tests the empty default constructor and the setter methods.
     * Creates an empty object, adds data, prints the data, and checks for accuracy.
     */
    @Test
    void testDefaultConstructorAndSetters() {
        // Create a new empty City object
        City city = new City();

        // Add data using the setter methods
        city.setCityName("Yangon");
        city.setCountry("Myanmar");
        city.setDistrict("Yangon District");
        city.setPopulation(5244000);

        // Print the added data to the screen
        System.out.println("--- Testing Default Constructor and Setters ---");
        System.out.println("Expected City Name: Yangon | Actual: " + city.getCityName());
        System.out.println("Expected Country: Myanmar | Actual: " + city.getCountry());
        System.out.println("Expected District: Yangon District | Actual: " + city.getDistrict());
        System.out.println("Expected Population: 5244000 | Actual: " + city.getPopulation());

        // Check if the getter methods return the correct added data
        assertEquals("Yangon", city.getCityName(), "The city name should be Yangon.");
        assertEquals("Myanmar", city.getCountry(), "The country name should be Myanmar.");
        assertEquals("Yangon District", city.getDistrict(), "The district name should be Yangon District.");
        assertEquals(5244000, city.getPopulation(), "The population should be 5244000.");

        System.out.println("Result: Default Constructor and Setters Test Passed!\n");
    }

    /**
     * Tests the parameterized constructor.
     * Creates an object with data already inside, prints the data, and checks for accuracy.
     */
    @Test
    void testParameterizedConstructor() {
        // Create a new City object and put data directly inside the parentheses
        City city = new City("Bangkok", "Thailand", "Bangkok District", 8280925);

        // Print the starting data to the screen
        System.out.println("--- Testing Parameterized Constructor ---");
        System.out.println("Expected City Name: Bangkok | Actual: " + city.getCityName());
        System.out.println("Expected Country: Thailand | Actual: " + city.getCountry());
        System.out.println("Expected District: Bangkok District | Actual: " + city.getDistrict());
        System.out.println("Expected Population: 8280925 | Actual: " + city.getPopulation());

        // Check if the getter methods return the correct starting data
        assertEquals("Bangkok", city.getCityName(), "The city name should be Bangkok.");
        assertEquals("Thailand", city.getCountry(), "The country name should be Thailand.");
        assertEquals("Bangkok District", city.getDistrict(), "The district name should be Bangkok District.");
        assertEquals(8280925, city.getPopulation(), "The population should be 8280925.");

        System.out.println("Result: Parameterized Constructor Test Passed!\n");
    }
}