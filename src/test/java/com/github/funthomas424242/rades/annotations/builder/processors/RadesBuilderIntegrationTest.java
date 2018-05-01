package com.github.funthomas424242.rades.annotations.builder.processors;

/*-
 * #%L
 * rades-annotations
 * %%
 * Copyright (C) 2018 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.github.funthomas424242.domain.Abteilung;
import com.github.funthomas424242.domain.AbteilungBuilder;
import com.github.funthomas424242.domain.Auto;
import com.github.funthomas424242.domain.CarBuilder;
import com.github.funthomas424242.domain.Familie;
import com.github.funthomas424242.domain.FamilieBuilder;
import com.github.funthomas424242.domain.Firma;
import com.github.funthomas424242.domain.FirmaAGErbauer;
import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.domain.PersonBuilder;
import com.github.funthomas424242.domain.TierAccessor;
import com.github.funthomas424242.rades.annotations.accessors.InvalidAccessorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RadesBuilderIntegrationTest {

    @BeforeAll
    public static void setUp() {
        // needed for Travis CI wich has en
        Locale.setDefault(new Locale("de", "DE"));
    }

    @Test
    @DisplayName("Alle Felder der Auto gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testAutoAlleFelderBefuellt() {
        final Auto firma = new CarBuilder()
                .withHersteller("Opel")
                .withMotor("Viertakt Motor")
                .withTyp("Corsa")
                .build();
        assertNotNull(firma);
    }


    @Test
    @DisplayName("Alle Felder der Firma gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testFirmaAlleFelderBefuellt() {
        final Firma firma = new FirmaAGErbauer()
                .withName("Musterfirma")
                .withBetriebeNr("AG-8788-S")
                .build();
        assertNotNull(firma);
    }

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
    @DisplayName("Alle Felder von Familie gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testFamilieAlleFelderBefuellt() {
        final Familie familie = new FamilieBuilder()
                .build();
        assertNotNull(familie);
    }

    @Test
    @DisplayName("Alle Felder von Person gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonAlleFelderBefuellt() {
        final Person person = new PersonBuilder()
                .withName("Mustermann")
                .withVorname("Max")
                .withBirthday(LocalDate.of(1968, 12, 25))
                .withGroesse(175)
                .build();
        person.addLieblingsfarbe(Person.Farbe.BLAU);
        assertNotNull(person);
    }

    @Test
    @DisplayName("Alle Felder von Person gültig befüllen aber ungültigen Accessor in build() nutzen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonMitInvalidAccessor() {
        Executable closureContainingCodeToTest = () -> {

            new PersonBuilder()
                    .withName("Mustermann")
                    .withVorname("Max")
                    .withBirthday(LocalDate.of(1968, 12, 25))
                    .withGroesse(175)
                    .build(TierAccessor.class);
        };
        assertThrows(InvalidAccessorException.class, closureContainingCodeToTest);

    }


    @Test
    @DisplayName("Pflichtfelder von Person nicht befüllt.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonPflichtfeldFehler() {

        Throwable exception = assertThrows(ValidationException.class, () -> {
            new PersonBuilder().build();
        });
        assertEquals("Person is not valid:\nname: darf nicht \"null\" sein", exception.getLocalizedMessage());
    }

    @Test
    @DisplayName("Optionale Felder von Person später befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonOptionaleFelderSpaeterBefuellt() {
        final Person person1 = new PersonBuilder()
                .withName("Mustermann")
                .build();
        assertNotNull(person1);
        final Person person = new PersonBuilder(person1)
                .withVorname("Max")
                .withBirthday(LocalDate.of(1968, 12, 25))
                .withGroesse(175)
                .build();
        person.addLieblingsfarbe(Person.Farbe.BLAU);
        assertNotNull(person);
    }

}
