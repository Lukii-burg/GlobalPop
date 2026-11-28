package com.globalpop.sprint3.Urban_vs_Rural_Population;

import com.globalpop.model.PopulationLivingInVsOutOfCities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Feature 25.
 * Connects to the live Docker MySQL database to check that the SQL query
 * returns total, city, and non-city populations for every country.
 */
public class Feature25IT {

    static Feature25 feature25;

    /**
     * Sets up the Feature25 object before running the test.
     */
    @BeforeAll
    static void init() {
        System.out.println("\n******************************************************************");
        System.out.println("            RUNNING INTEGRATION TESTS FOR FEATURE 25              ");
        System.out.println("******************************************************************");

        feature25 = new Feature25();
    }

    /**
     * Tests that the database returns population stats for all countries
     * and that total population equals city + non-city population.
     */
    @Test
    void testGetPopulationReports() throws SQLException {
        // Act: Run the SQL query against the database
        List<PopulationLivingInVsOutOfCities> reports = feature25.getPopulationReports();

        // Assert 1: Make sure we got a valid, non-empty list back
        assertNotNull(reports, "The returned list of country reports should not be null.");
        assertFalse(reports.isEmpty(), "The database should return country population reports.");

        // Assert 2: Verify the standard MySQL 'world' database returns all 239 countries
        assertEquals(239, reports.size(), "The world database should return 239 countries.");

        // Assert 3: Check alphabetical ordering and population math for the first record (Afghanistan)
        PopulationLivingInVsOutOfCities firstCountry = reports.get(0);
        assertEquals("Afghanistan", firstCountry.getName(), "First country alphabetically should be Afghanistan.");
        assertEquals(firstCountry.getTotalPopulation(),
                firstCountry.getUrbanPopulation() + firstCountry.getRuralPopulation(),
                "Total population must equal city population plus non-city population.");

        // Assert 4: Verify alphabetical order across all countries
        for (int i = 0; i < reports.size() - 1; i++) {
            assertTrue(reports.get(i).getName().compareToIgnoreCase(reports.get(i + 1).getName()) <= 0,
                    "Countries must be sorted alphabetically by name.");
        }

        System.out.println("Integration Test Passed: Successfully retrieved urban vs rural population stats for "
                + reports.size() + " countries!");
    }
}