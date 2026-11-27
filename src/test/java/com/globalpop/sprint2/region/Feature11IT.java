package com.globalpop.sprint2.region;

import com.globalpop.model.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature11IT {

    private final Feature11 feature11 = new Feature11();

    @Test
    @DisplayName("Should retrieve cities from California")
    void testRetrieveCitiesFromCalifornia() throws SQLException {
        List<City> cities =
                feature11.getCitiesInDistrict("California");

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        for (City city : cities) {
            assertEquals(
                    "California",
                    city.getDistrict(),
                    "Every returned city should belong to California"
            );
        }
    }

    @Test
    @DisplayName("Should return cities in descending population order")
    void testCitiesSortedByPopulation() throws SQLException {
        List<City> cities =
                feature11.getCitiesInDistrict("California");

        assertFalse(cities.isEmpty());

        for (int i = 0; i < cities.size() - 1; i++) {
            int currentPopulation =
                    cities.get(i).getPopulation();

            int nextPopulation =
                    cities.get(i + 1).getPopulation();

            assertTrue(
                    currentPopulation >= nextPopulation,
                    "Cities must be ordered by descending population"
            );
        }
    }

    @Test
    @DisplayName("Should support case-insensitive district search")
    void testCaseInsensitiveDistrictSearch() throws SQLException {
        List<City> cities =
                feature11.getCitiesInDistrict("california");

        assertFalse(cities.isEmpty());

        assertTrue(cities.stream().allMatch(
                city -> city.getDistrict()
                        .equalsIgnoreCase("california")
        ));
    }
}