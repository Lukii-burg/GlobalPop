package com.globalpop.model;

/**
 * Model class representing a General Population Lookup record.
 * Contains fields required for world, continent, region, country, district, or city population lookups:
 * Name, Population.
 */
public class GeneralPopulationLookups {

    /** The name of the area (World, Continent, Region, Country, District, City). */
    private String name;

    /** The total population of the area. */
    private long population;

    /**
     * Default constructor.
     */
    public GeneralPopulationLookups() {
    }

    /**
     * Parameterized constructor to initialize a GeneralPopulationLookup object.
     *
     * @param name       Name of the area
     * @param population Total population
     */
    public GeneralPopulationLookups(String name, long population) {
        this.name = name;
        this.population = population;
    }

    // --- Getter and Setter Methods ---

    /**
     * Gets the area name.
     *
     * @return Area name String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the area name.
     *
     * @param name Area name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the total population.
     *
     * @return Population as long.
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Sets the total population.
     *
     * @param population Population as long.
     */
    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "GeneralPopulationLookup{" +
                "name='" + name + '\'' +
                ", population=" + population +
                '}';
    }
}