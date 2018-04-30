package com.github.funthomas424242.rades.annotations.accessors;

import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.domain.PersonAccessor;
import com.github.funthomas424242.domain.PersonBuilder;
import com.github.funthomas424242.domain.Tier;
import com.github.funthomas424242.domain.TierAccessor;
import com.github.funthomas424242.domain.TierBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Kompatibilität für Accessors")
public class RadesAccessorCompatibillityTest {

    @DisplayName("Prüfe korrektes Package für @AddAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    @Test
    void testAddAccesoorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", AddAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesAddAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    void testRadesAddAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", RadesAddAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesNoAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    void testRadesNoAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", RadesNoAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @NoAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    void testNoAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", NoAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Delegation für toString() Methode im Accessor von Person.")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    void testToStringValidDelegation() {
        final Person person = new PersonBuilder().withName("Kratzert").withVorname("Johanna").build();
        final PersonAccessor personAccessor = new PersonAccessor(person);
        assertEquals("Kratzert, Johanna", personAccessor.toString());
    }

    @Test
    @DisplayName("Prüfe korrekte fehlende Delegation für toString() Methode im Accessor von Tier.")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    void testToStringInvalideDelegation() {
        final Tier tier = new TierBuilder().build();
        final TierAccessor tierAccessor = new TierAccessor(tier);
        assertNotEquals(tier.toString(), tierAccessor.toString());
    }

}
