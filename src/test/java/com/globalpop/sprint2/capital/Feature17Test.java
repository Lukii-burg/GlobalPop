
package com.globalpop.sprint2.capital;

import com.globalpop.model.CapitalCity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Feature17Test {

    @Test
    void testCapitalCityConstructor() {
        CapitalCity capital = new CapitalCity(
                "Naypyidaw",
                "Myanmar",
                925000
        );

        assertEquals("Naypyidaw", capital.getCapitalCityName());
        assertEquals("Myanmar", capital.getCountry());
        assertEquals(925000, capital.getPopulation());
    }

    @Test
    void testDefaultConstructor() {
        CapitalCity capital = new CapitalCity();

        assertNull(capital.getCapitalCityName());
        assertNull(capital.getCountry());
        assertEquals(0, capital.getPopulation());
    }

    @Test
    void testSetCapitalCityName() {
        CapitalCity capital = new CapitalCity();

        capital.setCapitalCityName("Tokyo");

        assertEquals("Tokyo", capital.getCapitalCityName());
    }

    @Test
    void testSetCountry() {
        CapitalCity capital = new CapitalCity();

        capital.setCountry("Japan");

        assertEquals("Japan", capital.getCountry());
    }

    @Test
    void testSetPopulation() {
        CapitalCity capital = new CapitalCity();

        capital.setPopulation(13960000);

        assertEquals(13960000, capital.getPopulation());
    }

    @Test
    void testZeroPopulation() {
        CapitalCity capital = new CapitalCity(
                "Test City",
                "Test Country",
                0
        );

        assertEquals(0, capital.getPopulation());
    }
}
