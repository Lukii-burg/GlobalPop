package com.globalpop.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests for the Language model.
 * It checks if data stores and retrieves correctly.
 * It also prints messages to the screen to show the results.
 */
public class LanguageTest {

    /**
     * Runs once before all test methods in this class start.
     * Prints a clear title banner to the console.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("                RUNNING UNIT TESTS FOR LANGUAGE                   ");
        System.out.println("******************************************************************\n");
    }

    /**
     * Tests the empty default constructor and the setter methods.
     * Creates an empty object, adds data, prints the data, and checks for accuracy.
     */
    @Test
    void testDefaultConstructorAndSetters() {
        // Create a new empty Language object
        Language lang = new Language();

        // Add data using the setter methods
        // Notice the 'L' after the speakers number because it is a long data type
        lang.setLanguage("Chinese");
        lang.setSpeakers(1190000000L);
        lang.setWorldPercentage(15.22);

        // Print the added data to the screen
        System.out.println("--- Testing Default Constructor and Setters ---");
        System.out.println("Expected Language: Chinese | Actual: " + lang.getLanguage());
        System.out.println("Expected Speakers: 1190000000 | Actual: " + lang.getSpeakers());
        System.out.println("Expected World Percentage: 15.22 | Actual: " + lang.getWorldPercentage());

        // Check if the getter methods return the correct added data
        // The third number (0.001) in the decimal check allows a tiny margin of error for double values
        assertEquals("Chinese", lang.getLanguage(), "The language should be Chinese.");
        assertEquals(1190000000L, lang.getSpeakers(), "The speakers count should be 1190000000.");
        assertEquals(15.22, lang.getWorldPercentage(), 0.001, "The world percentage should be 15.22.");

        System.out.println("Result: Default Constructor and Setters Test Passed!\n");
    }

    /**
     * Tests the parameterized constructor.
     * Creates an object with data already inside, prints the data, and checks for accuracy.
     */
    @Test
    void testParameterizedConstructor() {
        // Create a new Language object and put data directly inside the parentheses
        Language lang = new Language("Hindi", 850000000L, 10.55);

        // Print the starting data to the screen
        System.out.println("--- Testing Parameterized Constructor ---");
        System.out.println("Expected Language: Hindi | Actual: " + lang.getLanguage());
        System.out.println("Expected Speakers: 850000000 | Actual: " + lang.getSpeakers());
        System.out.println("Expected World Percentage: 10.55 | Actual: " + lang.getWorldPercentage());

        // Check if the getter methods return the correct starting data
        assertEquals("Hindi", lang.getLanguage(), "The language should be Hindi.");
        assertEquals(850000000L, lang.getSpeakers(), "The speakers count should be 850000000.");
        assertEquals(10.55, lang.getWorldPercentage(), 0.001, "The world percentage should be 10.55.");

        System.out.println("Result: Parameterized Constructor Test Passed!\n");
    }

    /**
     * Tests the toString method.
     * Creates an object, generates the string output, prints the text, and checks for exact formatting.
     */
    @Test
    void testToString() {
        // Create a new Language object with data
        Language lang = new Language("English", 900000000L, 11.23);

        // Call the toString method to get the formatted text
        String actualString = lang.toString();

        // Define the exact text format expected from the method.
        // Uses String.format for the percentage to match the model class logic exactly.
        String expectedString = "Language{language='English', speakers=900000000, worldPercentage=" + String.format("%.2f", 11.23) + "%}";

        // Print the string texts to the screen
        System.out.println("--- Testing toString Method ---");
        System.out.println("Expected String: " + expectedString);
        System.out.println("Actual String:   " + actualString);

        // Check if the actual string matches the expected string perfectly
        assertEquals(expectedString, actualString, "The toString method should format the text exactly as expected.");

        System.out.println("Result: toString Test Passed!\n");
    }
}

package com.globalpop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void testDefaultConstructor() {
        Language language = new Language();

        assertNull(language.getLanguage());
        assertEquals(0L, language.getSpeakers());
        assertEquals(0.0, language.getWorldPercentage());
    }

    @Test
    void testParameterizedConstructor() {
        Language language =
                new Language("English", 1500000000L, 18.5);

        assertEquals("English", language.getLanguage());
        assertEquals(1500000000L, language.getSpeakers());
        assertEquals(18.5, language.getWorldPercentage(), 0.001);
    }

    @Test
    void testSetLanguage() {
        Language language = new Language();

        language.setLanguage("Chinese");

        assertEquals("Chinese", language.getLanguage());
    }

    @Test
    void testSetSpeakers() {
        Language language = new Language();

        language.setSpeakers(1200000000L);

        assertEquals(1200000000L, language.getSpeakers());
    }

    @Test
    void testSetWorldPercentage() {
        Language language = new Language();

        language.setWorldPercentage(15.75);

        assertEquals(15.75, language.getWorldPercentage(), 0.001);
    }

    @Test
    void testToStringContainsLanguageInformation() {
        Language language =
                new Language("English", 1000000000L, 12.345);

        String result = language.toString();

        assertTrue(result.contains("English"));
        assertTrue(result.contains("1000000000"));
        assertTrue(result.contains("12.35%"));
    }
}
