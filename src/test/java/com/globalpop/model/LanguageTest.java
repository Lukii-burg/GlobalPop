
package com.globalpop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void testDefaultConstructor() {
        Language language = new Language();

        assertNull(language.getLanguage());
        assertEquals(0L, language.getSpeakers());
        assertEquals(0.0, language.getWorldPercentage());
    }

    @Test
    void testParameterizedConstructor() {
        Language language =
                new Language("English", 1500000000L, 18.5);

        assertEquals("English", language.getLanguage());
        assertEquals(1500000000L, language.getSpeakers());
        assertEquals(18.5, language.getWorldPercentage(), 0.001);
    }

    @Test
    void testSetLanguage() {
        Language language = new Language();

        language.setLanguage("Chinese");

        assertEquals("Chinese", language.getLanguage());
    }

    @Test
    void testSetSpeakers() {
        Language language = new Language();

        language.setSpeakers(1200000000L);

        assertEquals(1200000000L, language.getSpeakers());
    }

    @Test
    void testSetWorldPercentage() {
        Language language = new Language();

        language.setWorldPercentage(15.75);

        assertEquals(15.75, language.getWorldPercentage(), 0.001);
    }

    @Test
    void testToStringContainsLanguageInformation() {
        Language language =
                new Language("English", 1000000000L, 12.345);

        String result = language.toString();

        assertTrue(result.contains("English"));
        assertTrue(result.contains("1000000000"));
        assertTrue(result.contains("12.35%"));
    }
}
