package com.github.funthomas424242.rades.annotations.accessors.processors;

import com.github.funthomas424242.domain.Abteilung;
import com.github.funthomas424242.domain.AbteilungAccessor;
import com.github.funthomas424242.domain.AbteilungBuilder;
import com.github.funthomas424242.domain.Auto;
import com.github.funthomas424242.domain.CarAccessor;
import com.github.funthomas424242.domain.CarBuilder;
import com.github.funthomas424242.domain.Familie;
import com.github.funthomas424242.domain.FamilieAccessor;
import com.github.funthomas424242.domain.FamilieBuilder;
import com.github.funthomas424242.domain.Firma;
import com.github.funthomas424242.domain.FirmaAGErbauer;
import com.github.funthomas424242.domain.FirmaAGZugreifer;
import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.domain.PersonAccessor;
import com.github.funthomas424242.domain.PersonBuilder;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;


public class RadesAccessorIntegrationTest {

    @BeforeAll
    public static void setUp() {
        // needed for Travis CI wich has en
        Locale.setDefault(new Locale("de", "DE"));
    }

    @Test
    @DisplayName("Alle Felder von Auto lesen.")
    @Tags({@Tag("integration"), @Tag("accessor")})
    public void testAutoAlleFelderLesen() {
        final Auto auto = new CarBuilder()
                .withHersteller("Opel")
                .withMotor("Viertakt Motor")
                .withTyp("Corsa")
                .build();
        assertNotNull(auto);
        final CarAccessor car = new CarAccessor(auto);
        assertEquals("Opel", car.getHersteller());
        assertEquals("Viertakt Motor", car.getMotor());
        assertEquals("Corsa", car.getTyp());
        assertSame(auto, car.toAuto());
    }


    @Test
    @DisplayName("Alle Felder von Firma lesen.")
    @Tags({@Tag("integration"), @Tag("accessor")})
    public void testFirmaAlleFelderLesen() {
        final Firma firma = new FirmaAGErbauer()
                .withName("Musterfirma")
                .withBetriebeNr("AG-8788-S")
                .build();
        assertNotNull(firma);
        final FirmaAGZugreifer firmaAccessor = new FirmaAGZugreifer(firma);
        assertEquals("Musterfirma", firmaAccessor.getName());
        assertEquals("AG-8788-S", firmaAccessor.getBetriebeNr());
        assertSame(firma, firmaAccessor.toFirma());
    }

    @Test
    @DisplayName("Alle Felder von Abteilung lesen.")
    @Tags({@Tag("integration"), @Tag("accessor")})
    public void testAbteilungAlleFelderLesen() {
        final Abteilung abteilung = new AbteilungBuilder()
                .withName("Musterabteilung")
                .withAbteilungsNr("IT-8788")
                .build();
        assertNotNull(abteilung);
        final AbteilungAccessor accessor = new AbteilungAccessor(abteilung);
        assertEquals("Musterabteilung", accessor.getName());
        assertEquals("IT-8788", accessor.getAbteilungsNr());
        assertSame(abteilung, accessor.toAbteilung());

    }

    @Test
    @DisplayName("Alle Felder von Familie lesen.")
    @Tags({@Tag("integration"), @Tag("accessor")})
    public void testFamilieAlleFelderLesen() {
        final Familie familie = new FamilieBuilder()
                .build();
        assertNotNull(familie);
        final FamilieAccessor accessor = new FamilieAccessor(familie);
        assertNotNull(accessor);
        assertSame(familie, accessor.toFamilie());
    }

    @Test
    @DisplayName("Alle Felder von Person lesen.")
    @Tags({@Tag("integration"), @Tag("accessor")})
    public void testPersonAlleFelderLesen() {
        final Person person = new PersonBuilder()
                .withName("Mustermann")
                .withVorname("Max")
                .withBirthday(LocalDate.of(1968, 12, 25))
                .withGroesse(175)
                .withLieblingsfarben((HashSet<Person.Farbe>) Sets.newHashSet(Person.Farbe.BLAU))
                .build();
        assertNotNull(person);
        final PersonAccessor accessor = new PersonAccessor(person);
        assertEquals("Mustermann", accessor.getName());
        assertEquals("Max", accessor.getVorname());
        assertEquals(LocalDate.of(1968, 12, 25), accessor.getBirthday());
        assertEquals(175, accessor.getGroesse());
        assertEquals(Sets.newHashSet(Person.Farbe.BLAU), accessor.getLieblingsfarben().collect(Collectors.toSet()));
        assertSame(person, accessor.toPerson());
    }


}