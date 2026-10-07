package com.globalpop.model;

/**
 * Model class representing a Country record.
 * Contains fields required by the Country Report specification:
 * Code, Name, Continent, Region, Population, Capital.
 */
public class Country {

    /** The unique 3-letter country code. */
    private String code;

    /** The full name of the country. */
    private String name;

    /** The continent where the country is located. */
    private String continent;

    /** The geographic region of the country. */
    private String region;

    /** The total population of the country. */
    private long population;

    /** The name of the capital city. */
    private String capital;

    /**
     * Default constructor.
     */
    public Country() {
    }

    /**
     * Parameterized constructor to initialize a complete Country object.
     *
     * @param code       3-letter country code
     * @param name       Name of the country
     * @param continent  Continent name
     * @param region     Region name
     * @param population Total population
     * @param capital    Capital city name
     */
    public Country(String code, String name, String continent, String region, long population, String capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    // --- Getter and Setter Methods ---

    /**
     * Gets the country code.
     * @return Country code String.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the country code.
     * @param code Country code String.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the country name.
     * @return Country name String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the country name.
     * @param name Country name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the continent.
     * @return Continent name String.
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Sets the continent.
     * @param continent Continent name String.
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * Gets the region.
     * @return Region name String.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region.
     * @param region Region name String.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the total population.
     * @return Population as long.
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Sets the total population.
     * @param population Population as long.
     */
    public void setPopulation(long population) {
        this.population = population;
    }

    /**
     * Gets the capital city name.
     * @return Capital city name String.
     */
    public String getCapital() {
        return capital;
    }

    /**
     * Sets the capital city name.
     * @param capital Capital city name String.
     */
    public void setCapital(String capital) {
        this.capital = capital;
    }
}