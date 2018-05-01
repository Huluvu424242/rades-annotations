package com.github.funthomas424242.rades.annotations.builder;

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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RadesBuilderCompatibillityTest {

    @Test
    @DisplayName("Prüfe korrektes Package für @AddBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testAddBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", AddBuilder.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesAddBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testRadesAddBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", RadesAddBuilder.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesNoBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testRadesNoBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", RadesNoBuilder.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @NoBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testNoBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", NoBuilder.class.getPackage().getName());
    }


}
