package com.globalpop.sprint1_features;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Feature5Test {

    private final Feature5 feature5 = new Feature5();

    @Test
    @DisplayName("Should reject null continent")
    void testNullContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature5.getTopPopulatedCountriesInContinent(
                        null,
                        5
                )
        );
    }

    @Test
    @DisplayName("Should reject empty continent")
    void testEmptyContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature5.getTopPopulatedCountriesInContinent(
                        "",
                        5
                )
        );
    }

    @Test
    @DisplayName("Should reject blank continent")
    void testBlankContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature5.getTopPopulatedCountriesInContinent(
                        "   ",
                        5
                )
        );
    }

    @Test
    @DisplayName("Should reject zero limit")
    void testZeroLimit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature5.getTopPopulatedCountriesInContinent(
                        "Asia",
                        0
                )
        );
    }

    @Test
    @DisplayName("Should reject negative limit")
    void testNegativeLimit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature5.getTopPopulatedCountriesInContinent(
                        "Asia",
                        -1
                )
        );
    }
}