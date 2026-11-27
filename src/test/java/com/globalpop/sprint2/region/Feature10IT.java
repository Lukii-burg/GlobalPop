
package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature10IT {

    private final Feature10 feature10 = new Feature10();

    @Test
    @DisplayName("Should retrieve cities from Myanmar")
    void testRetrieveCitiesFromMyanmar() throws SQLException {

        List<City> cities =
                feature10.getCitiesByCountry("Myanmar");

        assertNotNull(cities,
                "City list should not be null");

        assertFalse(cities.isEmpty(),
                "Myanmar should contain city records");

        for (City city : cities) {
            assertEquals(
                    "Myanmar",
                    city.getCountry(),
                    "Every returned city should belong to Myanmar"
            );
        }
    }

    @Test
    @DisplayName("Should return cities in descending population order")
    void testCitiesSortedByPopulation() throws SQLException {

        List<City> cities =
                feature10.getCitiesByCountry("Myanmar");

        assertFalse(cities.isEmpty(),
                "City list should not be empty");

        for (int i = 0; i < cities.size() - 1; i++) {

            int currentPopulation =
                    cities.get(i).getPopulation();

            int nextPopulation =
                    cities.get(i + 1).getPopulation();

            assertTrue(
                    currentPopulation >= nextPopulation,
                    "Cities must be sorted by descending population"
            );
        }
    }

    @Test
    @DisplayName("Should support case-insensitive country search")
    void testCaseInsensitiveCountrySearch() throws SQLException {

        List<City> cities =
                feature10.getCitiesByCountry("myanmar");

        assertFalse(cities.isEmpty(),
                "Lowercase Myanmar search should return cities");

        assertTrue(cities.stream().allMatch(
                city -> city.getCountry().equalsIgnoreCase("myanmar")
        ));
    }
}
