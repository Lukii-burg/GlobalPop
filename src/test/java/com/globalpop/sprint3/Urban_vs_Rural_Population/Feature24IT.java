package com.globalpop.sprint3.Urban_vs_Rural_Population;

import com.globalpop.model.PopulationLivingInVsOutOfCities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Feature24IT {

    private final Feature24 feature24 = new Feature24();

    @Test
    @DisplayName("Should retrieve region population reports")
    void testPopulationReportsAreRetrievedFromDatabase()
            throws SQLException {

        List<PopulationLivingInVsOutOfCities> reports =
                feature24.getPopulationReports();

        assertNotNull(reports);
        assertFalse(reports.isEmpty());
    }

    @Test
    @DisplayName("Should return valid region population records")
    void testRegionRecordsContainValidInformation()
            throws SQLException {

        List<PopulationLivingInVsOutOfCities> reports =
                feature24.getPopulationReports();

        for (PopulationLivingInVsOutOfCities report : reports) {
            assertNotNull(report.getName());
            assertFalse(report.getName().isBlank());

            assertTrue(report.getTotalPopulation() >= 0);
            assertTrue(report.getUrbanPopulation() >= 0);
            assertTrue(report.getRuralPopulation() >= 0);
        }
    }

    @Test
    @DisplayName("Should keep total population equal to urban plus non-city population")
    void testPopulationTotalsAreConsistent()
            throws SQLException {

        List<PopulationLivingInVsOutOfCities> reports =
                feature24.getPopulationReports();

        for (PopulationLivingInVsOutOfCities report : reports) {
            assertEquals(
                    report.getTotalPopulation(),
                    report.getUrbanPopulation()
                            + report.getRuralPopulation(),
                    "Total population must equal urban plus non-city population"
            );
        }
    }

    @Test
    @DisplayName("Should return each region once")
    void testRegionReportsContainNoDuplicates()
            throws SQLException {

        List<PopulationLivingInVsOutOfCities> reports =
                feature24.getPopulationReports();

        Set<String> regionNames = new HashSet<>();

        for (PopulationLivingInVsOutOfCities report : reports) {
            assertTrue(
                    regionNames.add(report.getName()),
                    "Duplicate region found: " + report.getName()
            );
        }
    }
}