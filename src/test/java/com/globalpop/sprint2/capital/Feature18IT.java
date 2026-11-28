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
}