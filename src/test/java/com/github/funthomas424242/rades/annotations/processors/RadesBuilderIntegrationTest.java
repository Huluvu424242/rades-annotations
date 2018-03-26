package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.domain.Abteilung;
import com.github.funthomas424242.domain.AbteilungBuilder;
import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.domain.PersonBuilder;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class RadesBuilderIntegrationTest {

    @Test
    @DisplayName("Alle Felder von Abteilung gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testAbteilungAlleFelderBefuellt() {
        final Abteilung abteilung = new AbteilungBuilder()
                .withName("Musterabteilung")
                .withAbteilungsNr("IT-8788")
                .build();
        assertNotNull(abteilung);
    }

    @Test
    @DisplayName("Alle Felder von Person gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonAlleFelderBefuellt() {
        final Person person = new PersonBuilder()
                .withName("Mustermann")
                .withVorname("Max")
                .withBirthday(new Date(1968,12,25))
                .withGroesse(175)
                .withLieblingsfarben((HashSet<Person.Farbe>)Sets.newHashSet(Person.Farbe.BLAU))
                .build();
        assertNotNull(person);
    }

}