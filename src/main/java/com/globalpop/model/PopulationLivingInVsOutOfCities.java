package com.globalpop.model;

/**
 * Model class representing the population living in vs. out of cities (urban vs. rural).
 * Contains fields required for continent, region, or country population reports:
 * Name, Total Population, Urban Population (with %), and Rural Population (with %).
 */
public class PopulationLivingInVsOutOfCities {

    /** The name of the continent, region, or country. */
    private String name;

    /** The total population. */
    private long totalPopulation;

    /** The total population living in cities (urban). */
    private long urbanPopulation;

    /** The percentage of the total population living in cities. */
    private double urbanPercentage;

    /** The total population not living in cities (rural). */
    private long ruralPopulation;

    /** The percentage of the total population not living in cities. */
    private double ruralPercentage;

    /**
     * Default constructor.
     */
    public PopulationLivingInVsOutOfCities() {
    }

    /**
     * Parameterized constructor to initialize a PopulationLivingInOutCities object.
     * Automatically calculates urban and rural percentages based on populations provided.
     *
     * @param name             Name of the continent, region, or country
     * @param totalPopulation  Total population
     * @param urbanPopulation  Population living in cities
     * @param ruralPopulation  Population not living in cities
     */
    public PopulationLivingInVsOutOfCities(String name, long totalPopulation, long urbanPopulation, long ruralPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.urbanPopulation = urbanPopulation;
        this.ruralPopulation = ruralPopulation;
        calculatePercentages();
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
     * @return Total population as long.
     */
    public long getTotalPopulation() {
        return totalPopulation;
    }

    /**
     * Sets the total population and recalculates percentages.
     *
     * @param totalPopulation Total population as long.
     */
    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
        calculatePercentages();
    }

    /**
     * Gets the urban population (living in cities).
     *
     * @return Urban population as long.
     */
    public long getUrbanPopulation() {
        return urbanPopulation;
    }

    /**
     * Sets the urban population and recalculates percentages.
     *
     * @param urbanPopulation Urban population as long.
     */
    public void setUrbanPopulation(long urbanPopulation) {
        this.urbanPopulation = urbanPopulation;
        calculatePercentages();
    }

    /**
     * Gets the urban percentage.
     *
     * @return Urban percentage as double.
     */
    public double getUrbanPercentage() {
        return urbanPercentage;
    }

    /**
     * Gets the rural population (not living in cities).
     *
     * @return Rural population as long.
     */
    public long getRuralPopulation() {
        return ruralPopulation;
    }

    /**
     * Sets the rural population and recalculates percentages.
     *
     * @param ruralPopulation Rural population as long.
     */
    public void setRuralPopulation(long ruralPopulation) {
        this.ruralPopulation = ruralPopulation;
        calculatePercentages();
    }

    /**
     * Gets the rural percentage.
     *
     * @return Rural percentage as double.
     */
    public double getRuralPercentage() {
        return ruralPercentage;
    }

    /**
     * Private helper method to compute percentages safely, avoiding division by zero.
     */
    private void calculatePercentages() {
        if (totalPopulation > 0) {
            this.urbanPercentage = ((double) urbanPopulation / totalPopulation) * 100.0;
            this.ruralPercentage = ((double) ruralPopulation / totalPopulation) * 100.0;
        } else {
            this.urbanPercentage = 0.0;
            this.ruralPercentage = 0.0;
        }
    }

    @Override
    public String toString() {
        return "PopulationLivingInOutCities{" +
                "name='" + name + '\'' +
                ", totalPopulation=" + totalPopulation +
                ", urbanPopulation=" + urbanPopulation +
                ", urbanPercentage=" + String.format("%.2f", urbanPercentage) + "%" +
                ", ruralPopulation=" + ruralPopulation +
                ", ruralPercentage=" + String.format("%.2f", ruralPercentage) + "%" +
                '}';
    }
}