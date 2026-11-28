
package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature2IT {

    private final Feature2 feature2 = new Feature2();

    @Test
    @DisplayName("Should retrieve countries from Asia database records")
    void testRetrieveCountriesFromAsia() throws SQLException {

        List<Country> countries =
                feature2.getCountriesByContinent("Asia");

        assertNotNull(countries,
                "Country list should not be null");

        assertFalse(countries.isEmpty(),
                "Asia should contain country records");

        for (Country country : countries) {
            assertEquals("Asia", country.getContinent(),
                    "Every returned country should belong to Asia");
        }
    }

    @Test
    @DisplayName("Should return Asian countries in descending population order")
    void testCountriesSortedByPopulation() throws SQLException {

        List<Country> countries =
                feature2.getCountriesByContinent("Asia");

        assertFalse(countries.isEmpty(),
                "Country list should not be empty");

        for (int i = 0; i < countries.size() - 1; i++) {

            long currentPopulation =
                    countries.get(i).getPopulation();

            long nextPopulation =
                    countries.get(i + 1).getPopulation();

            assertTrue(currentPopulation >= nextPopulation,
                    "Countries must be sorted by descending population");
        }
    }

    @Test
    @DisplayName("Should retrieve countries using case-insensitive continent name")
    void testCaseInsensitiveContinentSearch() throws SQLException {

        List<Country> countries =
                feature2.getCountriesByContinent("asia");

        assertFalse(countries.isEmpty(),
                "Lowercase Asia search should return countries");

        assertTrue(countries.stream().allMatch(
                country -> country.getContinent().equalsIgnoreCase("asia")
        ));
    }
}
