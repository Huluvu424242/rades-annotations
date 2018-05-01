package com.github.funthomas424242.rades.annotations.accessors;

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

    @Test
    @DisplayName("Prüfe korrektes Package für @AddAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testAddAccesoorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", AddAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesAddAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testRadesAddAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", RadesAddAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesNoAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testRadesNoAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", RadesNoAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @NoAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testNoAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessors", NoAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Delegation für toString() Methode im Accessor von Person.")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testToStringValidDelegation() {
        final Person person = new PersonBuilder().withName("Kratzert").withVorname("Johanna").build();
        final PersonAccessor personAccessor = new PersonAccessor(person);
        assertEquals("Kratzert, Johanna", personAccessor.toString());
    }

    @Test
    @DisplayName("Prüfe korrekte fehlende Delegation für toString() Methode im Accessor von Tier.")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testToStringInvalideDelegation() {
        final Tier tier = new TierBuilder().build();
        final TierAccessor tierAccessor = new TierAccessor(tier);
        assertNotEquals(tier.toString(), tierAccessor.toString());
    }

}
