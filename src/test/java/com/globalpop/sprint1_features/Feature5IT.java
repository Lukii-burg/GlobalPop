package com.globalpop.sprint1_features;

import com.globalpop.model.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature5IT {

    private final Feature5 feature5 = new Feature5();

    @Test
    @DisplayName("Should retrieve top populated countries from Asia")
    void testRetrieveCountriesFromAsia() throws SQLException {
        List<Country> countries =
                feature5.getTopPopulatedCountriesInContinent(
                        "Asia",
                        5
                );

        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        assertEquals(5, countries.size());

        for (Country country : countries) {
            assertEquals(
                    "Asia",
                    country.getContinent(),
                    "Every returned country should belong to Asia"
            );
        }
    }

    @Test
    @DisplayName("Should return countries in descending population order")
    void testCountriesSortedByPopulation() throws SQLException {
        List<Country> countries =
                feature5.getTopPopulatedCountriesInContinent(
                        "Asia",
                        5
                );

        assertFalse(countries.isEmpty());

        for (int i = 0; i < countries.size() - 1; i++) {
            long currentPopulation =
                    countries.get(i).getPopulation();

            long nextPopulation =
                    countries.get(i + 1).getPopulation();

            assertTrue(
                    currentPopulation >= nextPopulation,
                    "Countries must be ordered by descending population"
            );
        }
    }

    @Test
    @DisplayName("Should support case-insensitive continent search")
    void testCaseInsensitiveContinentSearch() throws SQLException {
        List<Country> countries =
                feature5.getTopPopulatedCountriesInContinent(
                        "asia",
                        5
                );

        assertFalse(countries.isEmpty());

        assertTrue(countries.stream().allMatch(
                country -> country.getContinent()
                        .equalsIgnoreCase("asia")
        ));
    }
}