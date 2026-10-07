package com.globalpop.countriesbycontinentpopulation;

import java.sql.SQLException;
import java.util.List;

public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService() {
        this.countryRepository = new CountryRepository();
    }

    public List<Country> getCountriesByContinent(String continent)
            throws SQLException {

        if (continent == null || continent.isBlank()) {
            throw new IllegalArgumentException(
                    "Continent cannot be empty."
            );
        }

        return countryRepository.findCountriesByContinent(
                continent.trim()
        );
    }
}