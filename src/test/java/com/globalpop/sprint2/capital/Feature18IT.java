package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature18IT {

    private final Feature18 feature18 = new Feature18();

    @Test
    @DisplayName("Should retrieve capital cities from Asia")
    void testRetrieveCapitalCitiesFromAsia() throws SQLException {
        List<CapitalCity> capitalCities =
                feature18.getCapitalCitiesInContinent("Asia");

        assertNotNull(capitalCities);
        assertFalse(capitalCities.isEmpty());

        for (CapitalCity capitalCity : capitalCities) {
            assertNotNull(capitalCity.getCapitalCityName());
            assertFalse(capitalCity.getCapitalCityName().isBlank());

            assertNotNull(capitalCity.getCountry());
            assertFalse(capitalCity.getCountry().isBlank());

            assertTrue(capitalCity.getPopulation() >= 0);
        }
    }

    @Test
    @DisplayName("Should return capital cities in descending population order")
    void testCapitalCitiesSortedByPopulation() throws SQLException {
        List<CapitalCity> capitalCities =
                feature18.getCapitalCitiesInContinent("Asia");

        assertFalse(capitalCities.isEmpty());

        for (int i = 0; i < capitalCities.size() - 1; i++) {
            int currentPopulation =
                    capitalCities.get(i).getPopulation();

            int nextPopulation =
                    capitalCities.get(i + 1).getPopulation();

            assertTrue(
                    currentPopulation >= nextPopulation,
                    "Capital cities must be ordered by descending population"
            );
        }
    }

    @Test
    @DisplayName("Should support case-insensitive continent search")
    void testCaseInsensitiveContinentSearch() throws SQLException {
        List<CapitalCity> upperCaseResults =
                feature18.getCapitalCitiesInContinent("Asia");

        List<CapitalCity> lowerCaseResults =
                feature18.getCapitalCitiesInContinent("asia");

        assertFalse(lowerCaseResults.isEmpty());

        assertEquals(
                upperCaseResults.size(),
                lowerCaseResults.size(),
                "Uppercase and lowercase searches should return the same count"
        );
    }


    @Test
    @DisplayName("Should generate and display the Asia capital cities report")
    void testRunDisplaysReport() {
        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream originalOut = System.out;

        try {
            System.setOut(new java.io.PrintStream(
                    output,
                    true,
                    java.nio.charset.StandardCharsets.UTF_8
            ));

            feature18.run();

        } finally {
            System.setOut(originalOut);
        }

        String report = output.toString(
                java.nio.charset.StandardCharsets.UTF_8
        );

        assertTrue(report.contains(
                "Feature 18: All Capital Cities in a Continent"
        ));

        assertTrue(report.contains("Selected continent: Asia"));
        assertTrue(report.contains(
                "Report 18: CAPITAL CITIES IN CONTINENT: ASIA"
        ));

        assertTrue(report.contains("Capital City"));
        assertTrue(report.contains("Country"));
        assertTrue(report.contains("Population"));

        assertTrue(report.contains("Total capital cities displayed:"));
        assertFalse(report.contains(
                "No capital cities found for continent: Asia"
        ));
    }

}