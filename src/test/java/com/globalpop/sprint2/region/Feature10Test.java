
package com.globalpop.sprint2.region;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Feature10Test {

    private final Feature10 feature10 = new Feature10();

    @Test
    @DisplayName("Should reject null country name")
    void testNullCountryName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> feature10.getCitiesByCountry(null)
        );
    }

    @Test
    @DisplayName("Should reject empty country name")
    void testEmptyCountryName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> feature10.getCitiesByCountry("")
        );
    }

    @Test
    @DisplayName("Should reject blank country name")
    void testBlankCountryName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> feature10.getCitiesByCountry("   ")
        );
    }
}
