
package com.globalpop.sprint3.Language_Demographics;

import com.globalpop.model.Language;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Feature32Test {

    private String captureReportOutput(List<Language> reports) {

        Feature32 feature = new Feature32();

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
    void testReportDisplaysCorrectColumns() {
        String output = captureReportOutput(List.of(
                new Language("Chinese", 1200000000L, 15.20)
        ));

        assertTrue(output.contains("Language"));
        assertTrue(output.contains("Estimated Speakers"));
        assertTrue(output.contains("% of World Population"));
    }

    @Test
    void testReportDisplaysLanguageInformation() {
        String output = captureReportOutput(List.of(
                new Language("English", 900000000L, 11.23)
        ));

        assertTrue(output.contains("English"));
        assertTrue(output.contains("900,000,000"));
        assertTrue(output.contains("11.23%"));
    }

    @Test
    void testReportDisplaysAllFiveLanguages() {
        String output = captureReportOutput(List.of(
                new Language("Chinese", 1200000000L, 15.20),
                new Language("English", 900000000L, 11.23),
                new Language("Hindi", 600000000L, 7.50),
                new Language("Spanish", 500000000L, 6.20),
                new Language("Arabic", 300000000L, 4.00)
        ));

        assertTrue(output.contains("Chinese"));
        assertTrue(output.contains("English"));
        assertTrue(output.contains("Hindi"));
        assertTrue(output.contains("Spanish"));
        assertTrue(output.contains("Arabic"));

        assertTrue(output.contains("Total languages displayed: 5"));
    }

    @Test
    void testReportDisplaysEmptyList() {
        String output = captureReportOutput(List.of());

        assertTrue(output.contains("Language"));
        assertTrue(output.contains("Total languages displayed: 0"));
    }

    @Test
    void testReportDisplaysZeroSpeakers() {
        String output = captureReportOutput(List.of(
                new Language("Test Language", 0L, 0.0)
        ));

        assertTrue(output.contains("Test Language"));
        assertTrue(output.contains("0"));
        assertTrue(output.contains("0.00%"));
    }
}
