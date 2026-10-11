package com.globalpop.sprint3.language_report;

import com.globalpop.database.DatabaseConnection;
import com.globalpop.model.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Feature 32: Reports the estimated number of Chinese, English, Hindi,
 * Spanish, and Arabic speakers, ranked from greatest to smallest.
 */
public class Feature32 {

    /** Languages required by the feature specification. */
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Feature 32: Worldwide Language Speaker Report");
        System.out.println("Press Enter to generate the report.");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        List<Language> languageReports = getLanguageReports();

        if (languageReports.isEmpty()) {
            System.out.println("No language data was found.");
            return;
        }

        displayReport(languageReports);
    }

    /**
     * Retrieves estimated speaker totals and world-population percentages.
     *
     * Each country's population is multiplied by the percentage of its
     * population recorded as speaking the language. The estimates are then
     * summed across countries and ordered by speaker count.
     *
     * @return Languages ordered by estimated number of speakers.
     */
    private List<Language> getLanguageReports() {
        List<Language> reports = new ArrayList<>();

        String sql = """
                SELECT
                    language_totals.LanguageName,
                    language_totals.TotalSpeakers,
                    ROUND(
                        language_totals.TotalSpeakers * 100.0
                        / world_total.WorldPopulation,
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

            // Supply the five required languages safely as query parameters.
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

                    Language language = new Language(
                            languageName,
                            speakers,
                            worldPercentage
                    );

                    reports.add(language);
                }
            }

        } catch (SQLException exception) {
            System.err.println(
                    "Error retrieving language speaker data: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays the language reports in descending speaker-count order.
     *
     * @param reports Language reports returned by the database.
     */
    private void displayReport(List<Language> reports) {
        System.out.printf(
                "%-15s %25s %25s%n",
                "Language",
                "Estimated Speakers",
                "% of World Population"
        );

        System.out.println("-".repeat(67));

        for (Language language : reports) {
            System.out.printf(
                    "%-15s %,25d %24.2f%%%n",
                    language.getLanguage(),
                    language.getSpeakers(),
                    language.getWorldPercentage()
            );
        }
    }
}