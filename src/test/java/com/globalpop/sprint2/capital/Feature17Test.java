
package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Feature17Test {

    @Test
    void testCapitalCityConstructor() {
        CapitalCity capital = new CapitalCity(
                "Naypyidaw",
                "Myanmar",
                925000
        );

        assertEquals("Naypyidaw", capital.getCapitalCityName());
        assertEquals("Myanmar", capital.getCountry());
        assertEquals(925000, capital.getPopulation());
    }

    @Test
    void testDefaultConstructor() {
        CapitalCity capital = new CapitalCity();

        assertNull(capital.getCapitalCityName());
        assertNull(capital.getCountry());
        assertEquals(0, capital.getPopulation());
    }

    @Test
    void testSetCapitalCityName() {
        CapitalCity capital = new CapitalCity();

        capital.setCapitalCityName("Tokyo");

        assertEquals("Tokyo", capital.getCapitalCityName());
    }

    @Test
    void testSetCountry() {
        CapitalCity capital = new CapitalCity();

        capital.setCountry("Japan");

        assertEquals("Japan", capital.getCountry());
    }

    @Test
    void testSetPopulation() {
        CapitalCity capital = new CapitalCity();

        capital.setPopulation(13960000);

        assertEquals(13960000, capital.getPopulation());
    }

    @Test
    void testZeroPopulation() {
        CapitalCity capital = new CapitalCity(
                "Test City",
                "Test Country",
                0
        );

        assertEquals(0, capital.getPopulation());
    }


    @Test
    void testPrintCapitalCityReportWithEmptyList() {
        Feature17 feature = new Feature17();
        List<CapitalCity> emptyList = new ArrayList<>();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            feature.printCapitalCityReport(emptyList);
        } finally {
            System.setOut(originalOut);
        }

        String report = output.toString(StandardCharsets.UTF_8);

        assertTrue(report.contains(
                "No capital city data available to display."
        ));
    }

    @Test
    void testPrintCapitalCityReportWithValidList() {
        Feature17 feature = new Feature17();

        List<CapitalCity> capitals = List.of(
                new CapitalCity("Naypyidaw", "Myanmar", 925000),
                new CapitalCity("Tokyo", "Japan", 13960000)
        );

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            feature.printCapitalCityReport(capitals);
        } finally {
            System.setOut(originalOut);
        }

        String report = output.toString(StandardCharsets.UTF_8);

        assertTrue(report.contains("Naypyidaw"));
        assertTrue(report.contains("Tokyo"));
        assertTrue(report.contains("Myanmar"));
        assertTrue(report.contains("Japan"));
        assertTrue(report.contains("Total Capital Cities Listed: 2"));
    }

    @Test
    void testGenerateAndDisplayReportHandlesDatabaseError() {
        Feature17 feature = new Feature17() {
            @Override
            List<CapitalCity> getCapitalCitiesByPopulationDescending()
                    throws SQLException {
                throw new SQLException("Test database failure");
            }
        };

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;

        try {
            System.setErr(new PrintStream(output, true, StandardCharsets.UTF_8));
            assertDoesNotThrow(feature::generateAndDisplayReport);
        } finally {
            System.setErr(originalErr);
        }

        String errorOutput = output.toString(StandardCharsets.UTF_8);

        assertTrue(errorOutput.contains(
                "Database error while retrieving capital cities:"
        ));
        assertTrue(errorOutput.contains("Test database failure"));
    }

}
