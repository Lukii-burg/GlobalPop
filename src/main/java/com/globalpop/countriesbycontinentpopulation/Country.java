package com.globalpop.countriesbycontinentpopulation;

public class Country {

    private final String code;
    private final String name;
    private final String continent;
    private final String region;
    private final long population;
    private final Integer capital;

    public Country(String code,
                   String name,
                   String continent,
                   String region,
                   long population,
                   Integer capital) {

        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public long getPopulation() {
        return population;
    }

    public Integer getCapital() {
        return capital;
    }
}