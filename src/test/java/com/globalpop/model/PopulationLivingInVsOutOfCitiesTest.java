
package com.globalpop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationLivingInVsOutOfCitiesTest {

    @Test
    void testConstructorAndGetters() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000, 600000, 400000
                );

        assertEquals("Asia", report.getName());
        assertEquals(1000000, report.getTotalPopulation());
        assertEquals(600000, report.getUrbanPopulation());
        assertEquals(400000, report.getRuralPopulation());
    }

    @Test
    void testUrbanPercentage() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000, 600000, 400000
                );

        assertEquals(60.0, report.getUrbanPercentage(), 0.001);
    }

    @Test
    void testRuralPercentage() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000, 600000, 400000
                );

        assertEquals(40.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testZeroTotalPopulationReturnsZeroPercentages() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test", 0, 0, 0
                );

        assertEquals(0.0, report.getUrbanPercentage());
        assertEquals(0.0, report.getRuralPercentage());
    }

    @Test
    void testAllUrbanPopulation() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test", 1000, 1000, 0
                );

        assertEquals(100.0, report.getUrbanPercentage(), 0.001);
        assertEquals(0.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testAllRuralPopulation() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test", 1000, 0, 1000
                );

        assertEquals(0.0, report.getUrbanPercentage(), 0.001);
        assertEquals(100.0, report.getRuralPercentage(), 0.001);
    }
}
