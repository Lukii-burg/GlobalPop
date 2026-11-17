package com.globalpop.model;

/**
 * Model class representing a City record.
 *
 * Contains fields required by the City Report specification:
 * City Name, Country, District, Population.
 */
public class City {

    /** The name of the city. */
    private String cityName;

    /** The country where the city is located. */
    private String country;

    /** The district where the city is located. */
    private String district;

    /** The total population of the city. */
    private int population;

    /**
     * Default constructor.
     */
    public City() {
    }

    /**
     * Parameterized constructor to initialize a complete City object.
     *
     * @param cityName   Name of the city
     * @param country    Country name
     * @param district   District name
     * @param population Total population
     */
    public City(String cityName, String country, String district, int population) {
        this.cityName = cityName;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    // --- Getter and Setter Methods ---

    /**
     * Gets the city name.
     *
     * @return City name String.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the city name.
     *
     * @param cityName City name String.
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
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
     * Gets the district name.
     *
     * @return District name String.
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the district name.
     *
     * @param district District name String.
     */
    public void setDistrict(String district) {
        this.district = district;
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