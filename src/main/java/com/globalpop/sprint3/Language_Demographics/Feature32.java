package com.globalpop.sprint3.Language_Demographics;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Feature 32: Reports estimated speakers of Chinese, English,
 * Hindi, Spanish, and Arabic, ranked from greatest to smallest.
 */
public class Feature32 {

    private static final String[] LANGUAGES = {
            "Chinese",
            "English",
            "Hindi",
            "Spanish",
            "Arabic"
    };

    /**
     * Retrieves and displays the worldwide language speaker report.
     */
    public void generateAndDisplayReport() {

        System.out.println("\n==============================================================");
        System.out.println("       FEATURE 32: WORLDWIDE LANGUAGE SPEAKER REPORT");
        System.out.println("==============================================================");

        try {
            List<Language> languageReports = getLanguageReports();

            if (languageReports.isEmpty()) {
                System.out.println("No language data was found.");
                return;
            }

            displayReport(languageReports);

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving language speaker data: "
                            + exception.getMessage()
            );
        }
    }

    /**
     * Retrieves estimated speaker totals and world-population percentages.
     */
    private List<Language> getLanguageReports() throws SQLException {

        List<Language> reports = new ArrayList<>();

        String sql = """
                SELECT
                    language_totals.LanguageName,
                    language_totals.TotalSpeakers,
                    ROUND(
                        language_totals.TotalSpeakers * 100.0
                        / NULLIF(world_total.WorldPopulation, 0),
                        2
                    ) AS WorldPercentage
                FROM (
                    SELECT
                        countrylanguage.Language AS LanguageName,
                        ROUND(
                            SUM(
                                country.Population
                                * countrylanguage.Percentage / 100.0
                            )
                        ) AS TotalSpeakers
                    FROM countrylanguage
                    INNER JOIN country
                        ON country.Code = countrylanguage.CountryCode
                    WHERE countrylanguage.Language IN (?, ?, ?, ?, ?)
                    GROUP BY countrylanguage.Language
                ) AS language_totals
                CROSS JOIN (
                    SELECT SUM(Population) AS WorldPopulation
                    FROM country
                ) AS world_total
                ORDER BY language_totals.TotalSpeakers DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the five required languages as safe query parameters.
            for (int i = 0; i < LANGUAGES.length; i++) {
                statement.setString(i + 1, LANGUAGES[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String languageName =
                            resultSet.getString("LanguageName");

                    long speakers =
                            resultSet.getLong("TotalSpeakers");

                    double worldPercentage =
                            resultSet.getDouble("WorldPercentage");

                    reports.add(new Language(
                            languageName,
                            speakers,
                            worldPercentage
                    ));
                }
            }
        }

        return reports;
    }

    /**
     * Displays language reports in descending speaker-count order.
     */
    private void displayReport(List<Language> reports) {

        System.out.println("\n==========================================================================");

        System.out.printf(
                "%-15s %25s %25s%n",
                "Language",
                "Estimated Speakers",
                "% of World Population"
        );

        System.out.println("--------------------------------------------------------------------------");

        for (Language language : reports) {
            System.out.printf(
                    "%-15s %,25d %24.2f%%%n",
                    language.getLanguage(),
                    language.getSpeakers(),
                    language.getWorldPercentage()
            );
        }

        System.out.println("==========================================================================");
        System.out.printf("Total languages displayed: %d%n", reports.size());
    }
}

