
package com.globalpop.sprint1_features;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Feature2Test {

    private final Feature2 feature2 = new Feature2();

    @Test
    @DisplayName("Should reject null continent")
    void testNullContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature2.getCountriesByContinent(null)
        );
    }

    @Test
    @DisplayName("Should reject empty continent")
    void testEmptyContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature2.getCountriesByContinent("")
        );
    }

    @Test
    @DisplayName("Should reject blank continent")
    void testBlankContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature2.getCountriesByContinent("   ")
        );
    }
}
