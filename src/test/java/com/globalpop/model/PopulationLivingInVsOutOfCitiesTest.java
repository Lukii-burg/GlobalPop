
package com.globalpop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationLivingInVsOutOfCitiesTest {

    @Test
    void testConstructorAndGetters() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000L, 600000L, 400000L
                );

        assertEquals("Asia", report.getName());
        assertEquals(1000000L, report.getTotalPopulation());
        assertEquals(600000L, report.getUrbanPopulation());
        assertEquals(400000L, report.getRuralPopulation());
    }

    @Test
    void testUrbanPercentage() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000L, 600000L, 400000L
                );

        assertEquals(60.0, report.getUrbanPercentage(), 0.001);
    }

    @Test
    void testRuralPercentage() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000L, 600000L, 400000L
                );

        assertEquals(40.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testZeroTotalPopulationReturnsZeroPercentages() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test", 0L, 0L, 0L
                );

        assertEquals(0.0, report.getUrbanPercentage(), 0.001);
        assertEquals(0.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testAllUrbanPopulation() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test", 1000L, 1000L, 0L
                );

        assertEquals(100.0, report.getUrbanPercentage(), 0.001);
        assertEquals(0.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testAllRuralPopulation() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test", 1000L, 0L, 1000L
                );

        assertEquals(0.0, report.getUrbanPercentage(), 0.001);
        assertEquals(100.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testToString() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Europe", 740000000L, 550000000L, 190000000L
                );

        String expected =
                "PopulationLivingInVsOutOfCities{name='Europe', totalPopulation=740000000, urbanPopulation=550000000, ruralPopulation=190000000}";

        assertEquals(expected, report.toString());
    }
}
