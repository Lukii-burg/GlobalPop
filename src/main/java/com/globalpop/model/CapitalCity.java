package com.globalpop.model;

/**
 * Model class representing a Capital City record.
 * Contains fields required by the Capital City Report specification:
 * Capital City Name, Country, Population.
 */
public class CapitalCity {

    /** The name of the capital city. */
    private String capitalCityName;

    /** The country where the capital city is located. */
    private String country;

    /** The total population of the capital city. */
    private int population;

    /**
     * Default constructor.
     */
    public CapitalCity() {
    }

    /**
     * Parameterized constructor to initialize a complete CapitalCity object.
     *
     * @param capitalCityName Name of the capital city
     * @param country         Country name
     * @param population      Total population
     */
    public CapitalCity(String capitalCityName, String country, int population) {
        this.capitalCityName = capitalCityName;
        this.country = country;
        this.population = population;
    }

    // --- Getter and Setter Methods ---

    /**
     * Gets the capital city name.
     *
     * @return Capital city name String.
     */
    public String getCapitalCityName() {
        return capitalCityName;
    }

    /**
     * Sets the capital city name.
     *
     * @param capitalCityName Capital city name String.
     */
    public void setCapitalCityName(String capitalCityName) {
        this.capitalCityName = capitalCityName;
    }

    /**
     * Gets the country name.
     *
     * @return Country name String.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country name.
     *
     * @param country Country name String.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the total population.
     *
     * @return Population as int.
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Sets the total population.
     *
     * @param population Population as int.
     */
    public void setPopulation(int population) {
        this.population = population;
    }
}