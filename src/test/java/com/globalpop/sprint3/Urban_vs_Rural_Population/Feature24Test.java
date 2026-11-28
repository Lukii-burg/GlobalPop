package com.globalpop.sprint3.Urban_vs_Rural_Population;

import com.globalpop.model.PopulationLivingInVsOutOfCities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Feature24Test {

    @Test
    void testFeature24CanBeCreated() {
        Feature24 feature24 = new Feature24();

        assertNotNull(feature24);
    }

    @Test
    void testRegionPopulationPercentages() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Southeast Asia",
                        1000000L,
                        600000L,
                        400000L
                );

        assertEquals(60.0, report.getUrbanPercentage(), 0.001);
        assertEquals(40.0, report.getRuralPercentage(), 0.001);
    }

    @Test
    void testRegionPopulationTotalsAreConsistent() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Caribbean",
                        850000L,
                        500000L,
                        350000L
                );

        assertEquals(
                report.getTotalPopulation(),
                report.getUrbanPopulation() + report.getRuralPopulation()
        );
    }

    @Test
    void testZeroPopulationHasZeroPercentages() {
        PopulationLivingInVsOutOfCities report =
                new PopulationLivingInVsOutOfCities(
                        "Test Region",
                        0L,
                        0L,
                        0L
                );

        assertEquals(0.0, report.getUrbanPercentage(), 0.001);
        assertEquals(0.0, report.getRuralPercentage(), 0.001);
    }
}