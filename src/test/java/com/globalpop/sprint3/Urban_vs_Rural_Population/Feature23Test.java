
package com.globalpop.sprint3.Urban_vs_Rural_Population;

import com.globalpop.model.PopulationLivingInVsOutOfCities;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature23Test {

    private String captureReportOutput(
            List<PopulationLivingInVsOutOfCities> reports) {

        Feature23 feature = new Feature23();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try (PrintStream testOut =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {

            System.setOut(testOut);
            feature.displayReport(reports);

        } finally {
            System.setOut(originalOut);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void testReportDisplaysCorrectHeadingAndColumns() {
        String output = captureReportOutput(List.of(
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000L, 600000L, 400000L)
        ));

        assertTrue(output.contains("Continent"));
        assertTrue(output.contains("Total Population"));
        assertTrue(output.contains("City Population"));
        assertTrue(output.contains("Non-city Population"));
    }

    @Test
    void testReportDisplaysPopulationAndPercentages() {
        String output = captureReportOutput(List.of(
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000L, 600000L, 400000L)
        ));

        assertTrue(output.contains("Asia"));
        assertTrue(output.contains("1,000,000"));
        assertTrue(output.contains("600,000"));
        assertTrue(output.contains("400,000"));
        assertTrue(output.contains("60.00%"));
        assertTrue(output.contains("40.00%"));
    }

    @Test
    void testReportDisplaysMultipleContinents() {
        String output = captureReportOutput(List.of(
                new PopulationLivingInVsOutOfCities(
                        "Asia", 1000000L, 600000L, 400000L),
                new PopulationLivingInVsOutOfCities(
                        "Europe", 500000L, 350000L, 150000L),
                new PopulationLivingInVsOutOfCities(
                        "Africa", 800000L, 200000L, 600000L)
        ));

        assertTrue(output.contains("Asia"));
        assertTrue(output.contains("Europe"));
        assertTrue(output.contains("Africa"));
        assertTrue(output.contains("Total continents displayed: 3"));
    }

    @Test
    void testReportDisplaysZeroPopulationPercentages() {
        String output = captureReportOutput(List.of(
                new PopulationLivingInVsOutOfCities(
                        "Empty Region", 0L, 0L, 0L)
        ));

        assertTrue(output.contains("Empty Region"));
        assertTrue(output.contains("0.00%"));
        assertTrue(output.contains("Total continents displayed: 1"));
    }

    @Test
    void testReportDisplaysEmptyList() {
        String output = captureReportOutput(List.of());

        assertTrue(output.contains("Continent"));
        assertTrue(output.contains("Total continents displayed: 0"));
    }
}
