package com.globalpop.sprint2.capital;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Feature18Test {

    private final Feature18 feature18 = new Feature18();

    @Test
    @DisplayName("Should reject null continent")
    void testNullContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature18.getCapitalCitiesInContinent(null)
        );
    }

    @Test
    @DisplayName("Should reject empty continent")
    void testEmptyContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature18.getCapitalCitiesInContinent("")
        );
    }

    @Test
    @DisplayName("Should reject blank continent")
    void testBlankContinent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> feature18.getCapitalCitiesInContinent("   ")
        );
    }


    @Test
    @DisplayName("Should display a message when no capital cities are found")
    void testDisplayReportEmpty() {
        java.io.ByteArrayOutputStream output =
                new java.io.ByteArrayOutputStream();

        java.io.PrintStream originalOut = System.out;

        try {
            System.setOut(new java.io.PrintStream(output));

            feature18.displayReport(
                    java.util.Collections.emptyList(),
                    "Antarctica"
            );

        } finally {
            System.setOut(originalOut);
        }

        String report = output.toString();

        assertTrue(report.contains(
                "No capital cities found for continent: Antarctica"
        ));

        assertFalse(report.contains(
                "Total capital cities displayed:"
        ));
    }

}