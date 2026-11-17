
package com.globalpop.model;

/**
 * Model class representing population living in and outside cities.
 * Used by Features 23 and 24.
 */
public class PopulationLivingInVsOutOfCities {

    private String name;
    private long totalPopulation;
    private long urbanPopulation;
    private long ruralPopulation;

    public PopulationLivingInVsOutOfCities(
            String name,
            long totalPopulation,
            long urbanPopulation,
            long ruralPopulation) {

        this.name = name;
        this.totalPopulation = totalPopulation;
        this.urbanPopulation = urbanPopulation;
        this.ruralPopulation = ruralPopulation;
    }

    public String getName() {
        return name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public long getUrbanPopulation() {
        return urbanPopulation;
    }

    public long getRuralPopulation() {
        return ruralPopulation;
    }

    public double getUrbanPercentage() {
        if (totalPopulation == 0) {
            return 0.0;
        }

        return (urbanPopulation * 100.0) / totalPopulation;
    }

    public double getRuralPercentage() {
        if (totalPopulation == 0) {
            return 0.0;
        }

        return (ruralPopulation * 100.0) / totalPopulation;
    }

    @Override
    public String toString() {
        return "PopulationLivingInVsOutOfCities{" +
                "name='" + name + '\'' +
                ", totalPopulation=" + totalPopulation +
                ", urbanPopulation=" + urbanPopulation +
                ", ruralPopulation=" + ruralPopulation +
                '}';
    }
}
