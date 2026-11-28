
package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature17IT {

    @Test
    void testCapitalCitiesAreRetrievedFromDatabase()
            throws SQLException {

        Feature17 feature = new Feature17();

        List<CapitalCity> capitals =
                feature.getCapitalCitiesByPopulationDescending();

        assertNotNull(capitals);
        assertFalse(capitals.isEmpty());
    }

    @Test
    void testCapitalCityRecordsContainValidInformation()
            throws SQLException {

        Feature17 feature = new Feature17();

        List<CapitalCity> capitals =
                feature.getCapitalCitiesByPopulationDescending();

        for (CapitalCity capital : capitals) {

            assertNotNull(capital.getCapitalCityName());
            assertFalse(capital.getCapitalCityName().isBlank());

            assertNotNull(capital.getCountry());
            assertFalse(capital.getCountry().isBlank());

            assertTrue(capital.getPopulation() >= 0);
        }
    }

    @Test
    void testCapitalCitiesAreOrderedByPopulationDescending()
            throws SQLException {

        Feature17 feature = new Feature17();

        List<CapitalCity> capitals =
                feature.getCapitalCitiesByPopulationDescending();

        for (int i = 0; i < capitals.size() - 1; i++) {

            int currentPopulation =
                    capitals.get(i).getPopulation();

            int nextPopulation =
                    capitals.get(i + 1).getPopulation();

            assertTrue(
                    currentPopulation >= nextPopulation,
                    "Capital cities must be ordered by descending population"
            );
        }
    }


    @Test
    void testGenerateAndDisplayReport() {
        Feature17 feature = new Feature17();

        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream originalOut = System.out;

        try {
            System.setOut(new java.io.PrintStream(
                    output, true, java.nio.charset.StandardCharsets.UTF_8
            ));

            feature.generateAndDisplayReport();

        } finally {
            System.setOut(originalOut);
        }

        String report = output.toString(
                java.nio.charset.StandardCharsets.UTF_8
        );

        assertTrue(report.contains(
                "Report 17: ALL CAPITAL CITIES IN THE WORLD"
        ));

        assertTrue(report.contains("CAPITAL CITY"));
        assertTrue(report.contains("COUNTRY"));
        assertTrue(report.contains("POPULATION"));

        assertTrue(report.contains("Total Capital Cities Listed:"));

        assertFalse(report.contains(
                "No capital city data available to display."
        ));
    }

}
