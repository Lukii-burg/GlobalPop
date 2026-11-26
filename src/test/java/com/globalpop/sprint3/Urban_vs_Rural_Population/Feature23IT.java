
package com.globalpop.sprint3.Urban_vs_Rural_Population;

import com.globalpop.model.PopulationLivingInVsOutOfCities;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature23IT {

    @Test
    void testPopulationReportsAreRetrievedFromDatabase()
            throws SQLException {

        Feature23 feature = new Feature23();

        List<PopulationLivingInVsOutOfCities> reports =
                feature.getPopulationReports();

        assertNotNull(reports);
        assertFalse(reports.isEmpty());
    }

    @Test
    void testContinentRecordsContainValidInformation()
            throws SQLException {

        Feature23 feature = new Feature23();

        List<PopulationLivingInVsOutOfCities> reports =
                feature.getPopulationReports();

        for (PopulationLivingInVsOutOfCities report : reports) {
            assertNotNull(report.getName());
            assertFalse(report.getName().isBlank());

            assertTrue(report.getTotalPopulation() >= 0);
            assertTrue(report.getUrbanPopulation() >= 0);
        }
    }

    @Test
    void testPopulationTotalsAreConsistent()
            throws SQLException {

        Feature23 feature = new Feature23();

        List<PopulationLivingInVsOutOfCities> reports =
                feature.getPopulationReports();

        for (PopulationLivingInVsOutOfCities report : reports) {
            assertEquals(
                    report.getTotalPopulation(),
                    report.getUrbanPopulation()
                            + report.getRuralPopulation(),
                    "Total population must equal urban plus rural population"
            );
        }
    }

    @Test
    void testContinentReportsContainNoDuplicates()
            throws SQLException {

        Feature23 feature = new Feature23();

        List<PopulationLivingInVsOutOfCities> reports =
                feature.getPopulationReports();

        java.util.Set<String> continentNames = new java.util.HashSet<>();

        for (PopulationLivingInVsOutOfCities report : reports) {

            assertTrue(
                    continentNames.add(report.getName()),
                    "Duplicate continent found: " + report.getName()
            );
        }
    }

}
