package com.globalpop.sprint2.region;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Feature11Test {

    private final Feature11 feature11 = new Feature11();

    @Test
    @DisplayName("Should reject null district")
    void testNullDistrict() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature11.getCitiesInDistrict(null)
        );
    }

    @Test
    @DisplayName("Should reject empty district")
    void testEmptyDistrict() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature11.getCitiesInDistrict("")
        );
    }

    @Test
    @DisplayName("Should reject blank district")
    void testBlankDistrict() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature11.getCitiesInDistrict("   ")
        );
    }
}