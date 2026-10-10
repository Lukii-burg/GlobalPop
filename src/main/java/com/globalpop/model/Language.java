package com.globalpop.model;

/**
 * Model class representing a Language Report record.
 * Contains fields required for Report 32:
 * Language Name, Total Number of Speakers, and Percentage of World Population.
 */
public class Language {

    /** The name of the language (Chinese, English, Hindi, Spanish, Arabic). */
    private String language;

    /** The total number of speakers worldwide. */
    private long speakers;

    /** The percentage of the world population who speak this language. */
    private double worldPercentage;

    /**
     * Default constructor.
     */
    public Language() {
    }

    /**
     * Parameterized constructor to initialize a LanguageReport object.
     *
     * @param language        Name of the language
     * @param speakers        Total number of speakers
     * @param worldPercentage Percentage of the world population
     */
    public Language(String language, long speakers, double worldPercentage) {
        this.language = language;
        this.speakers = speakers;
        this.worldPercentage = worldPercentage;
    }

    // --- Getter and Setter Methods ---

    /**
     * Gets the language name.
     *
     * @return Language name String.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language name.
     *
     * @param language Language name String.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the total number of speakers.
     *
     * @return Total speakers as long.
     */
    public long getSpeakers() {
        return speakers;
    }

    /**
     * Sets the total number of speakers.
     *
     * @param speakers Total speakers as long.
     */
    public void setSpeakers(long speakers) {
        this.speakers = speakers;
    }

    /**
     * Gets the percentage of the world population.
     *
     * @return World percentage as double.
     */
    public double getWorldPercentage() {
        return worldPercentage;
    }

    /**
     * Sets the percentage of the world population.
     *
     * @param worldPercentage World percentage as double.
     */
    public void setWorldPercentage(double worldPercentage) {
        this.worldPercentage = worldPercentage;
    }

    @Override
    public String toString() {
        return "LanguageReport{" +
                "language='" + language + '\'' +
                ", speakers=" + speakers +
                ", worldPercentage=" + String.format("%.2f", worldPercentage) + "%" +
                '}';
    }
}