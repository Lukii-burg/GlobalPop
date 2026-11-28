
package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature22Test {

    private Feature22 feature22;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    private static final String TEST_REGION = "Southeast Asia";
    private static final int TEST_LIMIT = 5;

    private List<CapitalCity> validCapitalCities;

    @BeforeEach
    void setUp() {
        feature22 = new Feature22();

        output = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

        validCapitalCities = new ArrayList<>(List.of(
                new CapitalCity("Jakarta", "Indonesia", 9604900),
                new CapitalCity("Bangkok", "Thailand", 6320174),
                new CapitalCity("Singapore", "Singapore", 4017733),
                new CapitalCity("Yangon", "Myanmar", 3361700),
                new CapitalCity("Manila", "Philippines", 1581082)
        ));
    }

    private String getOutput() {
        return output.toString(StandardCharsets.UTF_8);
    }

    @org.junit.jupiter.api.AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void testRunWithNullRegion() {
        feature22.run(null, TEST_LIMIT);

        assertTrue(getOutput().contains("Region cannot be empty."));
    }

    @Test
    void testRunWithBlankRegion() {
        feature22.run("   ", TEST_LIMIT);

        assertTrue(getOutput().contains("Region cannot be empty."));
    }

    @Test
    void testRunWithEmptyRegion() {
        feature22.run("", TEST_LIMIT);

        assertTrue(getOutput().contains("Region cannot be empty."));
    }

    @Test
    void testRunWithZeroLimit() {
        feature22.run(TEST_REGION, 0);

        assertTrue(getOutput().contains("N must be a positive integer."));
    }

    @Test
    void testRunWithNegativeLimit() {
        feature22.run(TEST_REGION, -5);

        assertTrue(getOutput().contains("N must be a positive integer."));
    }

    @Test
    void testDisplayReportWithNullList() {
        feature22.displayReport(null, TEST_REGION, TEST_LIMIT);

        assertTrue(getOutput().contains(
                "No capital cities found for region: \"Southeast Asia\""
        ));
    }

    @Test
    void testDisplayReportWithEmptyList() {
        feature22.displayReport(
                new ArrayList<>(), TEST_REGION, TEST_LIMIT
        );

        assertTrue(getOutput().contains(
                "No capital cities found for region: \"Southeast Asia\""
        ));
    }

    @Test
    void testDisplayReportWithValidData() {
        feature22.displayReport(
                validCapitalCities, TEST_REGION, TEST_LIMIT
        );

        String result = getOutput();

        assertTrue(result.contains("TOP 5 POPULATED CAPITAL CITIES"));
        assertTrue(result.contains("SOUTHEAST ASIA"));
        assertTrue(result.contains("Jakarta"));
        assertTrue(result.contains("Indonesia"));
        assertTrue(result.contains("Bangkok"));
        assertTrue(result.contains("Thailand"));
    }

    @Test
    void testDisplayReportSkipsNullElements() {
        List<CapitalCity> citiesWithNull =
                new ArrayList<>(validCapitalCities);

        citiesWithNull.add(null);

        feature22.displayReport(
                citiesWithNull, TEST_REGION, TEST_LIMIT
        );

        String result = getOutput();

        assertTrue(result.contains("Jakarta"));
        assertTrue(result.contains("Manila"));
        assertFalse(result.contains("null |"));
    }

    @Test
    void testDisplayReportWithNullRegion() {
        feature22.displayReport(
                validCapitalCities, null, TEST_LIMIT
        );

        assertTrue(getOutput().contains("N/A"));
    }

    @Test
    void testRunHandlesDatabaseException() {

        Feature22 failingFeature = new Feature22() {

            @Override
            public List<CapitalCity> getTopNCapitalCitiesInRegion(
                    String region, int limit) throws SQLException {

                throw new SQLException("Simulated database failure");
            }
        };

        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;

        try {
            System.setErr(new PrintStream(
                    errorOutput,
                    true,
                    StandardCharsets.UTF_8
            ));

            failingFeature.run("Southeast Asia", 5);

        } finally {
            System.setErr(originalErr);
        }

        String errorMessage = errorOutput.toString(StandardCharsets.UTF_8);

        assertTrue(errorMessage.contains(
                "Database error while fetching Feature 22 data:"
        ));

        assertTrue(errorMessage.contains("Simulated database failure"));
    }

}
