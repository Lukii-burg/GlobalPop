
package com.globalpop.sprint3.Language_Demographics;

import com.globalpop.model.Language;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Feature32IT {

    private static final Set<String> EXPECTED_LANGUAGES =
            Set.of("Chinese", "English", "Hindi", "Spanish", "Arabic");

    @Test
    void testLanguageReportsAreRetrievedFromDatabase()
            throws SQLException {

        Feature32 feature = new Feature32();

        List<Language> reports = feature.getLanguageReports();

        assertNotNull(reports);
        assertFalse(reports.isEmpty());
    }

    @Test
    void testOnlyRequestedLanguagesAreReturned()
            throws SQLException {

        Feature32 feature = new Feature32();

        List<Language> reports = feature.getLanguageReports();

        for (Language language : reports) {
            assertTrue(
                    EXPECTED_LANGUAGES.contains(language.getLanguage()),
                    "Unexpected language: " + language.getLanguage()
            );
        }
    }

    @Test
    void testLanguageRecordsContainValidInformation()
            throws SQLException {

        Feature32 feature = new Feature32();

        List<Language> reports = feature.getLanguageReports();

        for (Language language : reports) {
            assertNotNull(language.getLanguage());
            assertFalse(language.getLanguage().isBlank());

            assertTrue(language.getSpeakers() >= 0);

            assertTrue(Double.isFinite(language.getWorldPercentage()));
            assertTrue(language.getWorldPercentage() >= 0.0);
            assertTrue(language.getWorldPercentage() <= 100.0);
        }
    }

    @Test
    void testLanguagesAreOrderedBySpeakerCountDescending()
            throws SQLException {

        Feature32 feature = new Feature32();

        List<Language> reports = feature.getLanguageReports();

        for (int i = 0; i < reports.size() - 1; i++) {
            assertTrue(
                    reports.get(i).getSpeakers()
                            >= reports.get(i + 1).getSpeakers(),
                    "Languages must be ordered by descending speaker count"
            );
        }
    }
}
