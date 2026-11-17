
package com.globalpop.model;

/**
 * Model class representing a general population lookup record.
 * Used for world, continent, region, country, district and city lookups.
 */
public class GeneralPopulationLookups {

    private String name;
    private long population;

    public GeneralPopulationLookups() {
    }

    public GeneralPopulationLookups(String name, long population) {
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "GeneralPopulationLookups{" +
                "name='" + name + '\'' +
                ", population=" + population +
                '}';
    }
}
